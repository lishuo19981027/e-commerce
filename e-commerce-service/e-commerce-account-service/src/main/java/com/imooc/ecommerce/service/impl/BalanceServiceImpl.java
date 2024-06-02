package com.imooc.ecommerce.service.impl;

import com.imooc.ecommerce.account.BalanceInfo;
import com.imooc.ecommerce.entity.EcommerceBalance;
import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.mapper.EcommerceBalanceMapper;
import com.imooc.ecommerce.service.IBalanceService;
import com.imooc.ecommerce.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BalanceServiceImpl implements IBalanceService {

    private final EcommerceBalanceMapper ecommerceBalanceMapper;

    public BalanceServiceImpl(EcommerceBalanceMapper ecommerceBalanceMapper) {
        this.ecommerceBalanceMapper = ecommerceBalanceMapper;
    }


    /*获取当前用户余额*/
    @Override
    public BalanceInfo getCurrentUserBalanceInfo() {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();
        BalanceInfo balanceInfo = new BalanceInfo(
                loginUserInfo.getId(), 0L
        );

        EcommerceBalance ecommerceBalance =
                ecommerceBalanceMapper.findByUserId(loginUserInfo.getId());
        if (null != ecommerceBalance) {
            balanceInfo.setBalance(ecommerceBalance.getBalance());
        } else {
            // 如果还没有用户余额记录, 这里创建出来，余额设定为0即可
            EcommerceBalance newBalance = new EcommerceBalance();
            newBalance.setUserId(loginUserInfo.getId());
            newBalance.setBalance(0L);
            ecommerceBalanceMapper.save(newBalance);
            log.info("init user balance record: [{}]",
                    newBalance.getId());
        }
        return balanceInfo;
    }

    /*扣减余额*/
    @Override
    public BalanceInfo deductBalance(BalanceInfo balanceInfo) {
        LoginUserInfo loginUserInfo = AccessContext.getLoginUserInfo();

        // 扣减用户余额的一个基本原则: 扣减额 <= 当前用户余额
        EcommerceBalance ecommerceBalance =
                ecommerceBalanceMapper.findByUserId(loginUserInfo.getId());
        if (null == ecommerceBalance
                || ecommerceBalance.getBalance() - balanceInfo.getBalance() < 0
        ) {
            throw new RuntimeException("user balance is not enough!");
        }

        Long sourceBalance = ecommerceBalance.getBalance();
        ecommerceBalance.setBalance(ecommerceBalance.getBalance() - balanceInfo.getBalance());
        ecommerceBalanceMapper.update(ecommerceBalance);
        log.info("deduct balance: [{}], [{}], [{}]",
                ecommerceBalance.getId(), sourceBalance,
                balanceInfo.getBalance());

        return new BalanceInfo(
                ecommerceBalance.getUserId(),
                ecommerceBalance.getBalance()
        );
    }

}

