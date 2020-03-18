package com.ningyuan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ningyuan.core.Context;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamsUtils {
    public static Map<String, String> requestParams2Map(Map requerstParams) {
        Map<String, String> params = new HashMap<String, String>();
        if (null != requerstParams) {
            for (Object o : requerstParams.entrySet()) {
                Map.Entry<String, String[]> entry = (Map.Entry) o;
                String value = "";
                for (String s : entry.getValue()) {
                    value += s;
                }
                params.put(entry.getKey(), value);
            }
        }
        return params;
    }

    public static <T> T mapToObject(Map<String, String> map, Class<T> tClass) {
        return JSONObject.parseObject(JSON.toJSONString(map)).toJavaObject(tClass);
    }

    public static String getLocalUrl() {
        HttpServletRequest request = Context.getHttpServletRequest();
        String localUrl = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
        if (!StringUtils.isEmpty(request.getQueryString())) {
            localUrl += "?" + request.getQueryString();
        }
        return localUrl;
    }

    public static String getRomote() {
        HttpServletRequest request = Context.getHttpServletRequest();
        return request.getScheme() + "://" + request.getServerName() + request.getContextPath();
    }

    public static String replaceAll(String template, Map<String, String> params) {
        Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            template = template.replace(matcher.group(), params.get(matcher.group(1)));
        }
        return template;
    }

    public static String getByKey(String template, String key) {

        Pattern pattern = Pattern.compile(key + "=(.*?)&");
        Matcher matcher = pattern.matcher(template + "&");
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}
