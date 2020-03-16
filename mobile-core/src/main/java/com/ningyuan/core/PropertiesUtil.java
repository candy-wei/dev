package com.ningyuan.core;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties getProperties(){
        return getProperties("config/jdbc.properties");
    }

    public static Properties getProperties(String propertiesPath){
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResource(propertiesPath).openStream();
            p.load(new InputStreamReader(is, "UTF-8"));
        } catch (Exception e) {
            //本地测试使用配置
            if(is == null){
                String path = PropertiesUtil.class.getResource("").getPath().replace("common-utils", "promote");
                path = path.replaceAll("classes.*", "classes/") + propertiesPath;
                try {
                    is = new FileInputStream(new File(path));
                    p.load(new InputStreamReader(is, "UTF-8"));
                } catch (Exception e1) {
                }
            }
        } finally {
            try {
                if(is != null){
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    private static void init(Properties props) throws SQLException {
        for(Object o : props.keySet()){
            String sql = "(UUID(), '";
            String key = (String) o;
            String value = (String) props.get(o);

            String[] array = key.split("\\.");
            String type = array.length > 0 ? array[0] : null;
            sql += type + "', '" + key + "', '" + value + "'),";
            System.out.println(sql);
        }
    }

    public static void main(String[] args) throws SQLException {
        init( getProperties());
    }

}
