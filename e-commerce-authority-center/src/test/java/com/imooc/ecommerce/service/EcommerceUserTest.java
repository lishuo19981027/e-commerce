package com.imooc.ecommerce.service;

import cn.hutool.crypto.digest.MD5;
import com.imooc.ecommerce.entity.EcommerceUser;
import com.imooc.ecommerce.mapper.EcommerceUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/*
* 验证表结构及mapper接口
* */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class EcommerceUserTest {

    @Resource
    private EcommerceUserMapper ecommerceUserMapper;

    @Test
    public void createUserRecord(){

        EcommerceUser ecommerceUser = new EcommerceUser().builder()
                .username("shuoli2@dev.com")
                .password(MD5.create().digestHex("12345678"))
                .extraInfo("{}")
                .createTime(new Date())
                .updateTime(new Date())
                .build();

        EcommerceUser byId = ecommerceUserMapper.findById(10L);
        System.out.println(byId.toString());
        ecommerceUserMapper.save(ecommerceUser);

    }

}
