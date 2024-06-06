package com.imooc.ecommerce.typehandler;

import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCategory;
import com.imooc.ecommerce.constant.GoodsStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@MappedTypes(GoodsStatus.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class GoodsStatusTypeHandler extends BaseTypeHandler<GoodsStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GoodsStatus goodsStatus, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, goodsStatus.getStatus());
    }

    @Override
    public GoodsStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int anInt = rs.getInt(columnName);
        return GoodsStatus.of(anInt);

    }

    @Override
    public GoodsStatus getNullableResult(ResultSet rs, int columnName) throws SQLException {
        int anInt = rs.getInt(columnName);
        return GoodsStatus.of(anInt);
    }

    @Override
    public GoodsStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int anInt = cs.getInt(columnIndex);
        return GoodsStatus.of(anInt);
    }
}
