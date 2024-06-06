package com.imooc.ecommerce.typehandler;

import com.imooc.ecommerce.constant.GoodsCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(GoodsCategory.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class GoodsCategoryTypeHandler extends BaseTypeHandler<GoodsCategory> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GoodsCategory goodsCategory, JdbcType jdbcType) throws SQLException {
        ps.setString(i, goodsCategory.getCode());
    }

    @Override
    public GoodsCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return code == null ? null : GoodsCategory.of(code);
    }

    @Override
    public GoodsCategory getNullableResult(ResultSet rs, int columnName) throws SQLException {
        String code = rs.getString(columnName);
        return code == null ? null : GoodsCategory.of(code);
    }

    @Override
    public GoodsCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return code == null ? null : GoodsCategory.of(code);
    }
}
