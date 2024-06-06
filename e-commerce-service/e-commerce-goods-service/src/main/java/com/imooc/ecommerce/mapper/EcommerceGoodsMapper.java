package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCategory;
import com.imooc.ecommerce.entity.EcommerceGoods;
import com.imooc.ecommerce.typehandler.BrandCategoryTypeHandler;
import com.imooc.ecommerce.typehandler.GoodsCategoryTypeHandler;
import com.imooc.ecommerce.typehandler.GoodsStatusTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper
@MappedTypes({GoodsCategoryTypeHandler.class,
        GoodsStatusTypeHandler.class, BrandCategoryTypeHandler.class})
public interface EcommerceGoodsMapper {

    /**
     * <h2>根据查询条件查询商品表, 并限制返回结果</h2>
     * select * from t_ecommerce_goods where goods_category = ? and brand_category = ?
     * and goods_name = ? limit 1;
     * */
    @Select("SELECT * FROM t_ecommerce_goods WHERE goods_category = #{goodsCategory.code} " +
            "AND brand_category = #{brandCategory.code} AND goods_name = #{goodsName} LIMIT 1")
    @Results({
            @Result(property = "goodsCategory", column = "goods_category", typeHandler = GoodsCategoryTypeHandler.class),
            @Result(property = "brandCategory", column = "brand_category", typeHandler = BrandCategoryTypeHandler.class),
            // Add mappings for other properties of EcommerceGoods here
    })
    Optional<EcommerceGoods> findFirst1ByGoodsCategoryAndBrandCategoryAndGoodsName(
            @Param("goodsCategory") GoodsCategory goodsCategory,
            @Param("brandCategory") BrandCategory brandCategory,
            String goodsName
    );

    int saveAll(@Param("targetGoods") List<EcommerceGoods> targetGoods);

    List<EcommerceGoods> findAllById(@Param("ids") List<Long> ids);


    @Select("SELECT * FROM t_ecommerce_goods ORDER BY id DESC")
    List<EcommerceGoods> findAll();

    @Update("UPDATE t_ecommerce_goods SET inventory = #{inventory} , update_time = now()"+
    "where id = #{id}")
    int update(EcommerceGoods ecommerceGoods);
}
