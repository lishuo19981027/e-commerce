package com.imooc.ecommerce.service.async.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.constant.GoodsConstant;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.goods.DeductGoodsInventory;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import com.imooc.ecommerce.mapper.EcommerceGoodsMapper;
import com.imooc.ecommerce.service.IGoodsService;
import com.imooc.ecommerce.vo.PageSimpleGoodsInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
     * <h1>商品微服务相关服务功能实现</h1>
     * */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements IGoodsService {

    private final StringRedisTemplate redisTemplate;
    private final EcommerceGoodsMapper ecommerceGoodsMapper;

    public GoodsServiceImpl(StringRedisTemplate redisTemplate,
                            EcommerceGoodsMapper ecommerceGoodsMapper) {
        this.redisTemplate = redisTemplate;
        this.ecommerceGoodsMapper = ecommerceGoodsMapper;
    }
    @Override
    public List<GoodsInfo> getGoodsInfoByTableId(TableId tableId) {
        // 详细的商品信息, 不能从 redis cache 中去拿
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId)
                .collect(Collectors.toList());
        log.info("get goods info by ids: [{}]", JSON.toJSONString(ids));

        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.findAllById(ids);

        return ecommerceGoods.stream()
                .map(EcommerceGoods::toGoodsInfo).collect(Collectors.toList());
    }

    @Override
    public PageSimpleGoodsInfo getSimpleGoodsInfoByPage(int page) {
        // 分页不能从 redis cache 中去拿
        if (page <= 1) {
            page = 1;   // 默认是第一页
        }

        // 这里分页的规则: 1页10条数据, 按照 id 倒序排列
        PageHelper.startPage(page, 10);
        List<EcommerceGoods> goodsList = ecommerceGoodsMapper.findAll();
        PageInfo<EcommerceGoods> ecommerceGoodsPageInfo =
                new PageInfo<>(goodsList);

        // 是否还有更多页: 总页数是否大于当前给定的页
        boolean hasMore = ecommerceGoodsPageInfo.getPages() > page;

        return new PageSimpleGoodsInfo(
                ecommerceGoodsPageInfo.getList().stream()
                        .map(EcommerceGoods::toSimple).collect(Collectors.toList()),
                hasMore
        );
    }


    @Override
    public List<SimpleGoodsInfo> getSimpleGoodsInfoByTableId(TableId tableId) {
        // 获取商品的简单信息, 可以从 redis cache 中去拿, 拿不到需要从 DB 中获取并保存到 Redis 里面
        // Redis 中的 KV 都是字符串类型
        List<Object> goodIds = tableId.getIds().stream()
                .map(i -> i.getId().toString()).collect(Collectors.toList());

        // FIXME 如果 cache 中查不到 goodsId 对应的数据, 返回的是 null, [null, null]
        List<Object> cachedSimpleGoodsInfos = redisTemplate.opsForHash()
                .multiGet(GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, goodIds)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // 如果从 Redis 中查到了商品信息, 分两种情况去操作
        if (CollectionUtils.isNotEmpty(cachedSimpleGoodsInfos)) {
            // 1. 如果从缓存中查询出所有需要的 SimpleGoodsInfo
            if (cachedSimpleGoodsInfos.size() == goodIds.size()) {
                log.info("get simple goods info by ids (from cache): [{}]",
                        JSON.toJSONString(goodIds));
                return parseCachedGoodsInfo(cachedSimpleGoodsInfos);
            } else {
                // 2. 一半从数据表中获取 (right), 一半从 redis cache 中获取 (left)
                List<SimpleGoodsInfo> left = parseCachedGoodsInfo(cachedSimpleGoodsInfos);
                // 取差集: 传递进来的参数 - 缓存中查到的 = 缓存中没有的
                Collection<Long> subtractIds = CollectionUtils.subtract(
                        goodIds.stream()
                                .map(g -> Long.valueOf(g.toString())).collect(Collectors.toList()),
                        left.stream()
                                .map(SimpleGoodsInfo::getId).collect(Collectors.toList())
                );
                // 缓存中没有的, 查询数据表并缓存
                List<SimpleGoodsInfo> right = queryGoodsFromDBAndCacheToRedis(
                        new TableId(subtractIds.stream().map(TableId.Id::new)
                                .collect(Collectors.toList()))
                );
                // 合并 left 和 right 并返回
                log.info("get simple goods info by ids (from db and cache): [{}]",
                        JSON.toJSONString(subtractIds));
                return new ArrayList<>(CollectionUtils.union(left, right));
            }
        } else {
            // 从 redis 里面什么都没有查到
            return queryGoodsFromDBAndCacheToRedis(tableId);
        }
    }

    /**
     * <h2>将缓存中的数据反序列化成 Java Pojo 对象</h2>
     * */
    private List<SimpleGoodsInfo> parseCachedGoodsInfo(List<Object> cachedSimpleGoodsInfo) {

        return cachedSimpleGoodsInfo.stream()
                .map(s -> JSON.parseObject(s.toString(), SimpleGoodsInfo.class))
                .collect(Collectors.toList());
    }

    /**
     * <h2>从数据表中查询数据, 并缓存到 Redis 中</h2>
     * */
    private List<SimpleGoodsInfo> queryGoodsFromDBAndCacheToRedis(TableId tableId) {

        // 从数据表中查询数据并做转换
        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get simple goods info by ids (from db): [{}]",
                JSON.toJSONString(ids));
        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.findAllById(ids);
        List<SimpleGoodsInfo> result = ecommerceGoods.stream()
                .map(EcommerceGoods::toSimple).collect(Collectors.toList());
        // 将结果缓存, 下一次可以直接从 redis cache 中查询
        log.info("cache goods info: [{}]", JSON.toJSONString(ids));

        Map<String, String> id2JsonObject = new HashMap<>(result.size());
        result.forEach(g -> id2JsonObject.put(g.getId().toString(), JSON.toJSONString(g)));
        // 保存到 Redis 中
        redisTemplate.opsForHash().putAll(
                GoodsConstant.ECOMMERCE_GOODS_DICT_KEY, id2JsonObject);
        return result;
    }

    @Override
    public Boolean deductGoodsInventory(List<DeductGoodsInventory> deductGoodsInventories) {

        // 检验下参数是否合法
        deductGoodsInventories.forEach(d -> {
            if (d.getCount() <= 0) {
                throw new RuntimeException("purchase goods count need > 0");
            }
        });

        List<EcommerceGoods> ecommerceGoods = ecommerceGoodsMapper.findAllById(deductGoodsInventories.stream()
                                .map(DeductGoodsInventory::getGoodsId).collect(Collectors.toList()));

        // 根据传递的 goodsIds 查询不到商品对象, 抛异常
        if (CollectionUtils.isEmpty(ecommerceGoods)) {
            throw new RuntimeException("can not found any goods by request");
        }

        // 查询出来的商品数量与传递的不一致, 抛异常
        if (ecommerceGoods.size() != deductGoodsInventories.size()) {
            throw new RuntimeException("request is not valid");
        }

        // goodsId -> DeductGoodsInventory
        Map<Long, DeductGoodsInventory> goodsId2Inventory =
                deductGoodsInventories.stream().collect(
                        Collectors.toMap(DeductGoodsInventory::getGoodsId,
                                Function.identity())
                );

        // 检查是不是可以扣减库存, 再去扣减库存
        ecommerceGoods.forEach(g -> {
            Long currentInventory = g.getInventory();
            Integer needDeductInventory = goodsId2Inventory.get(g.getId()).getCount();
            if (currentInventory < needDeductInventory) {
                log.error("goods inventory is not enough: [{}], [{}]",
                        currentInventory, needDeductInventory);
                throw new RuntimeException("goods inventory is not enough: " + g.getId());
            }
            // 扣减库存
            g.setInventory(currentInventory - needDeductInventory);
            log.info("deduct goods inventory: [{}], [{}], [{}]", g.getId(),
                    currentInventory, g.getInventory());
        });

        ecommerceGoods.forEach(g ->{
                ecommerceGoodsMapper.update(g);});

        log.info("deduct goods inventory done");

        return true;
    }
}
