package com.imooc.ecommerce.service;

import com.imooc.ecommerce.vo.UsernameAndPassword;

/*
    * JWT相关服务接口定义
    * */
public interface IJWTService {

    /*
    * 生成JWT Token，使用默认的超时时间
    * */
    String generateToken(String username , String password) throws Exception;

    /*
     * 生成指定超时时间的JWT Token，使用默认的超时时间
    * */
    String generateToken(String username , String password,int expire) throws Exception;

    /*注册用户并生成Token返回*/
    String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception;
}
