package com.ningyuan.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.*;

public class CommonUtil {
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;

        Object obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        List<Field> declaredFields = new ArrayList<Field>();
        declaredFields.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
        declaredFields.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));

        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(value)) {
                map.put(field.getName(), value);
            }
        }
        return map;
    }

    public static boolean isNullCondition(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return false;
        }
        List<Field> declaredFields = new ArrayList<Field>();
        declaredFields.addAll(Arrays.asList(obj.getClass().getDeclaredFields()));
        declaredFields.addAll(Arrays.asList(obj.getClass().getSuperclass().getDeclaredFields()));
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String value = (String) field.get(obj);
            if (!StringUtils.isEmpty(value)) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, Object> objectToMap(Object... obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object o : obj) {
            map.putAll(objectToMap(o));
        }
        return map;
    }

    public static String dou2Str(Double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (d < 1) {
            return "0" + df.format(d);
        }
        return df.format(d);
    }

    public static MultiValueMap<String, String> mapToMultiValueMap(Map<String, Object> map, String prefix) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
        prefix = StringUtils.isEmpty(prefix) ? "" : prefix + ".";
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            multiValueMap.add(prefix + entry.getKey(), entry.getValue().toString());
        }
        return multiValueMap;
    }

    public static String inputStream2Str(InputStream in) {
        String str = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuffer sb = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            in.reset();
            return sb.toString();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return str;
    }

    public static String captureName(String promoteType) {
        char[] chars = promoteType.toCharArray();
        chars[0] -= 32;
        return new String(chars);
    }

    public static <T> void copyProperties(T source, T target) {
        List<Field> declaredFields = new ArrayList<Field>();
        declaredFields.addAll(Arrays.asList(source.getClass().getDeclaredFields()));
        declaredFields.addAll(Arrays.asList(source.getClass().getSuperclass().getDeclaredFields()));
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
            }
        }
    }

    public static String camelCaseJoin(String... strs) {
        String result = "";
        for (String str : strs) {
            if (org.apache.commons.lang.StringUtils.isEmpty(result)) {
                result = str;
            } else {
                result += str.substring(0, 1).toUpperCase() + str.substring(1);
            }
        }
        return result;
    }

    /**
     * 从集合list中随机抽取 num个元素 , (不放回抽取)
     *
     * @param list
     * @param num
     * @return
     */
    public static <T> List<T> getRandomFromList(List<T> list, Long num) {
        List<T> res = new ArrayList<>();
        while (res.size() < num) {
            int index = RandomUtils.nextInt(0, list.size());
            res.add(list.get(index));
            list.remove(list.get(index));
        }
        return res;
    }

    public static void initPageInfo(Integer pageNum, Integer pageSize, Integer defPageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = defPageSize;
        }
        PageHelper.startPage(pageNum, pageSize, false);
    }
    public static Page initPageInfoWithCount(Integer pageNum, Integer pageSize, Integer defPageSize) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            pageSize = defPageSize;
        }
        return PageHelper.startPage(pageNum, pageSize, true);
    }
}
