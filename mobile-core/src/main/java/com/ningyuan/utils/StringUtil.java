package com.ningyuan.utils;

import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串转换类
 * <p>
 * 项目名称：WeiChatService
 * 类名称：StringUtil
 * 类描述：
 * 创建人：zhouling
 * 创建时间：Nov 12, 2013 5:16:48 PM
 * 修改备注：
 */
public class StringUtil {

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }


    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    public static String byteToHexStr(byte mByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    /**
     * 计算采用utf-8编码方式时字符串所占字节数
     *
     * @param content
     * @return
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            try {
                // 汉字采用utf-8编码时占3个字节   
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return size;
    }

    public static String replaceAll(String template, Map<String, String> params) {
        Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            if (params.containsKey(matcher.group(1))) {
                template = template.replace(matcher.group(), params.get(matcher.group(1)));
            }
        }
        return template;
    }

    public static String replaceAll(String template, Map<String, String> params, String... others) {
        template = replaceAll(template, params);
        template = replaceAll(template, others);
        return template;
    }

    public static String replaceAll(String template, Object dto) {
        if (dto == null) {
            return template;
        }

        if (dto instanceof String) {
            return replaceAll(template, (String) dto, "");
        }

        Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = pattern.matcher(template);
        Class<?> clazz = dto.getClass();
        while (matcher.find()) {
            try {
                Field field = clazz.getDeclaredField(matcher.group(1));
                field.setAccessible(true);
                if (null == field.get(dto)) {
                    continue;
                }
                template = template.replace(matcher.group(), (String) field.get(dto));
            } catch (Exception e) {
            }
        }
        return template;
    }

    public static String replaceAll(String template, String... params) {
        List<String> paramList = Arrays.asList(params);
        Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = pattern.matcher(template);
        int i = 0;
        while (matcher.find()) {
            template = template.replace(matcher.group(), paramList.get(i));
            i++;
        }
        return template;
    }


    public static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static String codeParseURLDecoderDecode(String URI) throws UnsupportedEncodingException {
        if (StringUtils.isNotEmpty(URI)) {
            return URI;
        }
        return URLDecoder.decode(URI, "UTF-8");
    }

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
}
