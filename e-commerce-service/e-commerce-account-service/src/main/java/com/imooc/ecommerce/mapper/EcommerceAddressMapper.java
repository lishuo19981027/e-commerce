package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.entity.EcommerceAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EcommerceAddressMapper {

    //根据主键id查
    List<EcommerceAddress> findById(@Param("id") Long id);

    //根据主键id批量查
    List<EcommerceAddress> findAllById(@Param("ids") List<Long> ids);

    // 根据用户ID查询所有地址
    @Select("SELECT * FROM t_ecommerce_address WHERE user_id = #{userId}")
    List<EcommerceAddress> findAllByUserId(@Param("userId") Long userId);


    // 批量插入新地址
    int saveAll(@Param("addresses") List<EcommerceAddress> addresses);
}
