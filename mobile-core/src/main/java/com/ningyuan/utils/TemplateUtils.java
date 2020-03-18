package com.ningyuan.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateUtils {
    public static String replaceAll(String template, Map<String, Object> params) {
        Pattern pattern = Pattern.compile("\\$\\{(.*?)}");
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            template = template.replace(matcher.group(), (String) params.get(matcher.group(1)));
        }
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
                String val;
                try {
                    val = (String) field.get(dto);
                } catch (ClassCastException e) {
                    val = field.get(dto).toString();
                }
                template = template.replace(matcher.group(), val);
            } catch (Exception e) {
                e.printStackTrace();
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
}
