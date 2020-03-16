package com.ningyuan.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Properties properties = new Properties();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)throws BeansException {
        properties = props;
        loadPropsFromDb();
        super.processProperties(beanFactory, properties);
    }

    public static Properties getProperties(){
        return properties;
    }

    public static void loadPropsFromDb() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String sql = "select param_key, param_value from sys_params";
            conn = JdbcUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String key = (String) resultSet.getObject("param_key");
                String value = (String) resultSet.getObject("param_value");
                if(StringUtils.isEmpty(value)){
                   continue;
                }
                properties.setProperty(key, value);
            }

            /**
             * 开发场配置, dev.properties不用提交
             * */
            Properties dev = PropertiesUtil.getProperties("config/dev.properties");
            properties.putAll(dev);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.releaseConn(pstmt, resultSet, conn);
        }
    }
}
