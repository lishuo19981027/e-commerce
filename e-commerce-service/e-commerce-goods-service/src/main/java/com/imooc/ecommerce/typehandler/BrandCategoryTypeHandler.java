package com.imooc.ecommerce.typehandler;

import com.imooc.ecommerce.constant.BrandCategory;
import com.imooc.ecommerce.constant.GoodsCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(BrandCategory.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class BrandCategoryTypeHandler extends BaseTypeHandler<BrandCategory> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i , BrandCategory parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public BrandCategory getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return code == null ? null : BrandCategory.of(code);
    }

    @Override
    public BrandCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return code == null ? null : BrandCategory.of(code);
    }

    @Override
    public BrandCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return code == null ? null : BrandCategory.of(code);
    }
}

