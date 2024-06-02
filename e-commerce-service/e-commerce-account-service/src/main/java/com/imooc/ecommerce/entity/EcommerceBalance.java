package com.imooc.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

     /*
     * 用户账户表实体类定义
     * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceBalance {

    //主键id
    private Long id;

    //用户id
    private Long userId;

    //账户余额
    private Long balance;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

}
