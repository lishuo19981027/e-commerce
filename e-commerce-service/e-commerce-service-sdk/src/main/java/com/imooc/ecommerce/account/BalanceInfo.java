package com.imooc.ecommerce.account;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/*用户账户余额信息*/
@ApiModel(description = "用户账户余额信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceInfo {

    @ApiModelProperty(value = "余额所属用户id")
    private Long userId;


    @ApiModelProperty(value = "用户账户余额")
    private Long balance;

}
