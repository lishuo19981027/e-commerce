package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.entity.EcommerceUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EcommerceUserMapper {

    /*根据用户名查询实体对象*/
    EcommerceUser findByUsername(String username);

    /*根据用户名和密码查询实体对象*/
    EcommerceUser findByUsernameAndPassword(String username , String password);

    /*添加用户*/
    int save(EcommerceUser ecommerceUser);

    /*根据主键id查找用户*/
    EcommerceUser findById(Long id);
}
