package com.ningyuan.core;


import java.sql.*;
import java.util.Properties;

public class JdbcUtil {

    // 表示定义数据库的用户名
    private static String USERNAME;

    // 定义数据库的密码
    private static String PASSWORD;

    // 定义数据库的驱动信息
    private static String DRIVER;

    // 定义访问数据库的地址
    private static String URL;

    static {
        //加载数据库配置信息，并给相关的属性赋值
        loadConfig();
    }

    /**
     * 加载数据库配置信息，并给相关的属性赋值
     */
    public static void loadConfig() {
        try {
            Properties prop = PropertiesUtil.getProperties();
            USERNAME = prop.getProperty("jdbc.mapper.username");
            PASSWORD = prop.getProperty("jdbc.mapper.password");
            DRIVER = prop.getProperty("jdbc.driver.class.name");
            URL = prop.getProperty("jdbc.mapper.url");
        } catch (Exception e) {
            throw new RuntimeException("读取数据库配置文件异常！", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER); // 注册驱动
            return DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接
        } catch (Exception e) {
            throw new RuntimeException("get connection error!", e);
        }
    }

    /**
     * 释放资源
     */
    public static void releaseConn(PreparedStatement pstmt, ResultSet resultSet, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
