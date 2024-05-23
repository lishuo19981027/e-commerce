package com.imooc.ecommerce.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
    * 登录用户信息
    * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo {

    /*用户id*/
    private Long id;

    /*用户名*/
    private String username;
}
