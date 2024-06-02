package com.imooc.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/*用户地址信息*/
@ApiModel(description = "用户地址信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressInfo {

    @ApiModelProperty(value = "地址所属用户 id")
    private Long userId;


    @ApiModelProperty(value = "用户的所有地址信息")
    private List<AddressItem>  addressItems;

    /*单个的地址信息*/
    @ApiModel(description = "用户地址信息")
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressItem{

        @ApiModelProperty(value = "地址表主键 id")
        private Long id;

        @ApiModelProperty(value = "用户姓名")
        private String username;

        @ApiModelProperty(value = "电话")
        private String phone;

        @ApiModelProperty(value = "省")
        private String province;

        @ApiModelProperty(value = "市")
        private String city;

        @ApiModelProperty(value = "详细地址")
        private String addressDetail;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "更新时间")
        private Date updateTime;

        public AddressItem(Long id) {
            this.id = id;
        }

        /*将AddressItem转换为UserAddress*/
        public UserAddress toUserAddress(){
            UserAddress userAddress = new UserAddress().builder()
                    .username(this.username)
                    .phone(this.phone)
                    .province(this.province)
                    .city(this.city)
                    .addressDetail(this.addressDetail)
                    .build();
            return userAddress;
        }
    }

}
