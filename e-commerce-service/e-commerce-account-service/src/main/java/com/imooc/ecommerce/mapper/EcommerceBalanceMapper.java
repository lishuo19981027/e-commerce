package com.imooc.ecommerce.mapper;

import com.imooc.ecommerce.entity.EcommerceBalance;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EcommerceBalanceMapper {

    // 根据用户ID查询账户余额
    EcommerceBalance findByUserId(@Param("userId") Long userId);

    // 插入新的账户余额信息
    int save(EcommerceBalance balance);

    // 更新账户余额信息
    @Update("UPDATE t_ecommerce_balance SET balance = #{balance}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int update(EcommerceBalance balance);

    // 删除账户余额信息
    @Delete("DELETE FROM t_ecommerce_balance WHERE id = #{id}")
    int delete(@Param("id") Long id);
}
