package com.imooc.ecommerce.entity;

import com.imooc.ecommerce.account.AddressInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

    /*
    * 用户地址表实体类定义
    * */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcommerceAddress {

    //自增主键
    private Long id;

    //用户id
    private Long userId;

    //用户名
    private String username;

    //电话
    private String phone;

    //省
    private String province;

    //市
    private String city;

    //详细地址
    private String addressDetail;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;


    //根据 userId + AddressItem得到EcommerceAddress
    public static EcommerceAddress to(Long userId , AddressInfo.AddressItem addressItem){

        EcommerceAddress ecommerceAddress = new EcommerceAddress().builder()
                .userId(userId)
                .id(addressItem.getId())
                .username(addressItem.getUsername())
                .phone(addressItem.getPhone())
                .province(addressItem.getPhone())
                .city(addressItem.getCity())
                .addressDetail(addressItem.getAddressDetail())
                .createTime(addressItem.getCreateTime())
                .updateTime(addressItem.getUpdateTime())
                .build();

        return ecommerceAddress;
    }

    //将EcommerceAddress 对象转成AddressInfo
    public AddressInfo.AddressItem toAddressItem(){

        AddressInfo.AddressItem addressItem = new AddressInfo.AddressItem();

        BeanUtils.copyProperties(this , addressItem);

        return addressItem;

    }
}
