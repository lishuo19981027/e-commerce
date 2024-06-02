package com.imooc.ecommerce.service;


import com.imooc.ecommerce.account.BalanceInfo;

/*用户余额服务接口定义*/
public interface IBalanceService {

    /*获取当前用户余额信息*/
    BalanceInfo getCurrentUserBalanceInfo();

    /*扣减用户余额 */
    BalanceInfo deductBalance(BalanceInfo balanceInfo);
}
