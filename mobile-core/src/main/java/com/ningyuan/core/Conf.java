package com.ningyuan.core;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

public class Conf {

    public static Properties getProperties(){
        Properties p = CustomizedPropertyConfigurer.getProperties();
        if(p == null || p.size() == 0){
            CustomizedPropertyConfigurer.loadPropsFromDb();
        }
        return p;
    }

    public static String get(String keyWithDef) {
        String key = StringUtils.substringBefore(keyWithDef, ":");
        if (!containsKey(key)) {
            String after = StringUtils.substringAfter(keyWithDef, ":");
            if (StringUtils.isNotEmpty(after)) {
                return after;
            }
            throw new RuntimeException("未配置该参数:" + key);
        }
        return (String) getProperties().get(key);
    }

    public static boolean containsKey(String key){
        return  getProperties().containsKey(key);
    }

    public static boolean containsValue(String value){
        return  getProperties().containsValue(value);
    }

    public static boolean enable(String key){
            return Boolean.parseBoolean(get(key + ":true"));
    }

}
