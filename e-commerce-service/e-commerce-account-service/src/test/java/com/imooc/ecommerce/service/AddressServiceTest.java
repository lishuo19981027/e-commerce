package com.imooc.ecommerce.service;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

/*用户地址相关服务功能测试*/
@Slf4j
@SpringBootTest
public class AddressServiceTest extends BaseTest{

    @Autowired
    private IAddressService addressService;

    /*测试创建用户地址信息*/
    @Test
    public void testCreateAddressInfo(){
        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();
        addressItem.setUsername("Lishuo");
        addressItem.setPhone("1880000000");
        addressItem.setProvince("某市1");
        addressItem.setCity("某市1");
        addressItem.setAddressDetail("一个地方");

        log.info("test create address info: [{}]", JSON.toJSONString(
                addressService.createAddressInfo(
                        new AddressInfo(loginUserInfo.getId(),
                                Collections.singletonList(addressItem))
                )
        ));
    }

    /**
     * <h2>测试获取当前登录用户地址信息</h2>
     * */
    @Test
    public void testGetCurrentAddressInfo() {

        log.info("test get current user info: [{}]", JSON.toJSONString(
                addressService.getCurrentAddressInfo()
        ));
    }

    /**
     * <h2>测试通过 id 获取用户地址信息</h2>
     * */
    @Test
    public void testGetAddressInfoById() {

        log.info("test get address info by id: [{}]", JSON.toJSONString(
                addressService.getAddressInfoById(10L)
        ));
    }

    /**
     * <h2>测试通过 TableId 获取用户地址信息</h2>
     * */
    @Test
    public void testGetAddressInfoByTableId() {

        log.info("test get address info by table id: [{}]", JSON.toJSONString(
                addressService.getAddressInfoByTableId(
                        new TableId(Collections.singletonList(new TableId.Id(1L)))
                )
        ));
    }
}
