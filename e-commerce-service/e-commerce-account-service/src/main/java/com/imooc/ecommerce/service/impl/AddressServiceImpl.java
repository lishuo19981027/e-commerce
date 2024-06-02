package com.imooc.ecommerce.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.spring.util.BeanUtils;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import com.imooc.ecommerce.entity.EcommerceAddress;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.mapper.EcommerceAddressMapper;
import com.imooc.ecommerce.service.IAddressService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)/*任意异常回滚*/
public class AddressServiceImpl implements IAddressService {

    private final EcommerceAddressMapper ecommerceAddressMapper;

    public AddressServiceImpl(EcommerceAddressMapper ecommerceAddressMapper) {
        this.ecommerceAddressMapper = ecommerceAddressMapper;
    }

    /*存储多个地址信息*/
    @Override
    public TableId createAddressInfo(AddressInfo addressInfo) {

        //不能从参数中拿
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        //将传递的参数转换为实体对象
        List<EcommerceAddress> ecommerceAddresses = addressInfo.getAddressItems().stream()
                .map(a -> EcommerceAddress.to(loginUserInfo.getId(), a))
                .collect(Collectors.toList());

        //保存到数据表并返回记录的id给调用方
        ecommerceAddressMapper.saveAll(ecommerceAddresses);

        // 获取插入后的ID列表
        List<Long> ids = ecommerceAddresses.stream()
                .map(EcommerceAddress::getId)
                .collect(Collectors.toList());
        log.info("create address info: [{}],[{}]",loginUserInfo.getId(),
                JSON.toJSONString(ids));

        return new TableId(
                ids.stream().map(TableId.Id::new).collect(Collectors.toList())
        );
    }

    @Override
    public AddressInfo getCurrentAddressInfo() {

        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        //根据 userId查询到用户的地址信息，再实现转换
        List<EcommerceAddress> ecommerceAddresses =
                ecommerceAddressMapper.findAllByUserId(loginUserInfo.getId());
        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem).collect(Collectors.toList());

        return new AddressInfo(loginUserInfo.getId(),addressItems);
    }

    @Override
    public AddressInfo getAddressInfoById(Long id) {
        List<EcommerceAddress> ecommerceAddress = ecommerceAddressMapper.findById(id);
        if(null == ecommerceAddress){
            throw new RuntimeException("address is not exist");
        }
        List<AddressInfo.AddressItem> addressItems =
                ecommerceAddress.stream().map(EcommerceAddress::toAddressItem)
                        .collect(Collectors.toList());
        return new AddressInfo(
                ecommerceAddress.get(0).getUserId(),addressItems);
    }

    @Override
    public AddressInfo getAddressInfoByTableId(TableId tableId) {

        List<Long> ids = tableId.getIds().stream()
                .map(TableId.Id::getId).collect(Collectors.toList());
        log.info("get address info by table id:[{}]",JSON.toJSONString(ids));

        List<EcommerceAddress> ecommerceAddresses = ecommerceAddressMapper.findAllById(ids);
        if(CollectionUtils.isEmpty(ecommerceAddresses)){
            return  new AddressInfo(-1L, Collections.emptyList());
        }

        List<AddressInfo.AddressItem> addressItems = ecommerceAddresses.stream()
                .map(EcommerceAddress::toAddressItem)
                .collect(Collectors.toList());

        return new AddressInfo(
                ecommerceAddresses.get(0).getUserId(),addressItems
        );
    }


}
