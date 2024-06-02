package com.imooc.ecommerce.service;

import com.imooc.ecommerce.account.AddressInfo;
import com.imooc.ecommerce.common.TableId;

/*用户地址服务接口定义*/
public interface IAddressService {

    //创建用户地址信息
    TableId createAddressInfo(AddressInfo addressInfo);

    //获取当前登录的用户地址信息
    AddressInfo getCurrentAddressInfo();

    //根据用户id查询地址信息
    AddressInfo getAddressInfoById(Long id);

    //根据TableId批量查询地址信息
    AddressInfo getAddressInfoByTableId(TableId tableId);

}
