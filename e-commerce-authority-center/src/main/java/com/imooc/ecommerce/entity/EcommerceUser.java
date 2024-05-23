package com.imooc.ecommerce.entity;


import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/*
    * 用户表实体类定义
    * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceUser {

    //主键id
    private Long id;

    //用户名
    private String username;

    //密码MD5
    private String password;

    //额外信息
    private String extraInfo;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
