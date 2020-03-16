package com.ningyuan.base.typeHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: zongrui_cai
 * @Date: 2019/6/24 10:51
 */
public class StrHiddenTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i,s);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String str =resultSet.getString(s);
        return strHidden(str);
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String str =resultSet.getString(i);
        return strHidden(str);
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getString(i);
    }
    private static String strHidden(String str){
        if(StringUtils.isEmpty(str)){return "";}
        int len = StringUtils.length(str);
        if(len<=2){return str;}
        return StringUtils.substring(str,0,len/3)+"***"+ StringUtils.substring(str,(len/3)*2,len);
    }
}
