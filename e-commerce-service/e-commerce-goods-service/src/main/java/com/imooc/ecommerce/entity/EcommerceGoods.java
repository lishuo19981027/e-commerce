package com.imooc.ecommerce.entity;


import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCategory;
import com.imooc.ecommerce.constant.GoodsStatus;
import com.imooc.ecommerce.goods.GoodsInfo;
import com.imooc.ecommerce.goods.SimpleGoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import java.util.Date;

/**
 * <h1>商品表实体类定义</h1>
 * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceGoods {

    /** 自增主键 */
    private Long id;

    /** 商品类型 */
    private GoodsCategory goodsCategory;

    /** 品牌分类 */
    private BrandCategory brandCategory;

    /** 商品名称 */
    private String goodsName;

    /** 商品名称 */
    private String goodsPic;

    /** 商品描述信息 */
    private String goodsDescription;

    /** 商品状态 */
    private GoodsStatus goodsStatus;

    /** 商品价格: 单位: 分、厘 */
    private Integer price;

    /** 总供应量 */
    private Long supply;

    /** 库存 */
    private Long inventory;

    /** 商品属性, json 字符串存储 */
    private String goodsProperty;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /**
     * <h2>将 GoodsInfo 转成实体对象</h2>
     * */
    public static EcommerceGoods to(GoodsInfo goodsInfo) {

        EcommerceGoods ecommerceGoods = new EcommerceGoods();

        ecommerceGoods.setGoodsCategory(GoodsCategory.of(goodsInfo.getGoodsCategory()));
        ecommerceGoods.setBrandCategory(BrandCategory.of(goodsInfo.getBrandCategory()));
        ecommerceGoods.setGoodsName(goodsInfo.getGoodsName());
        ecommerceGoods.setGoodsPic(goodsInfo.getGoodsPic());
        ecommerceGoods.setGoodsDescription(goodsInfo.getGoodsDescription());
        ecommerceGoods.setGoodsStatus(GoodsStatus.ONLINE);  // 可以增加一个审核的过程
        ecommerceGoods.setPrice(goodsInfo.getPrice());
        ecommerceGoods.setSupply(goodsInfo.getSupply());
        ecommerceGoods.setInventory(goodsInfo.getSupply());
        ecommerceGoods.setGoodsProperty(
                JSON.toJSONString(goodsInfo.getGoodsProperty())
        );

        return ecommerceGoods;
    }

    /**
     * <h2>将实体对象转成 GoodsInfo 对象</h2>
     * */
    public GoodsInfo toGoodsInfo() {

        GoodsInfo goodsInfo = new GoodsInfo();

        goodsInfo.setId(this.id);
        goodsInfo.setGoodsCategory(this.goodsCategory.getCode());
        goodsInfo.setBrandCategory(this.brandCategory.getCode());
        goodsInfo.setGoodsName(this.goodsName);
        goodsInfo.setGoodsPic(this.goodsPic);
        goodsInfo.setGoodsDescription(this.goodsDescription);
        goodsInfo.setGoodsStatus(this.goodsStatus.getStatus());
        goodsInfo.setPrice(this.price);
        goodsInfo.setGoodsProperty(
                JSON.parseObject(this.goodsProperty, GoodsInfo.GoodsProperty.class)
        );
        goodsInfo.setSupply(this.supply);
        goodsInfo.setInventory(this.inventory);
        goodsInfo.setCreateTime(this.createTime);
        goodsInfo.setUpdateTime(this.updateTime);

        return goodsInfo;
    }

    /**
     * <h2>将实体对象转成 SimpleGoodsInfo 对象</h2>
     * */
    public SimpleGoodsInfo toSimple() {

        SimpleGoodsInfo goodsInfo = new SimpleGoodsInfo();

        goodsInfo.setId(this.id);
        goodsInfo.setGoodsName(this.goodsName);
        goodsInfo.setGoodsPic(this.goodsPic);
        goodsInfo.setPrice(this.price);

        return goodsInfo;
    }
}
