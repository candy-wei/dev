package com.ningyuan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 16:08 on 2018/10/15.
 * @Modified by:
 */
public class RESTUtils {

    public static final ApplicationContext ac;

    static {
        ac = new ClassPathXmlApplicationContext("applicationContext-commonUtils.xml");
    }

    public static RestTemplate rest() {
        return ac.getBean(RestTemplate.class);
    }

    public static HttpHeaders header() {
        return header(MediaType.APPLICATION_JSON_UTF8);
    }

    public static HttpHeaders header(MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        //返回JSON
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    public static <T> ResponseEntity<T> post(String url, Class<T> tClass, Object... params) {
        HttpEntity httpEntity;
        if (params == null || params.length == 0) {
            httpEntity = new HttpEntity(header());
        } else {
            httpEntity = new HttpEntity(params[0], header());
        }
        return rest().postForEntity(url, httpEntity, tClass);
    }

    public static <T> ResponseEntity<T> formPost(String url, MultiValueMap<String, String> params, Class<T> tClass) {
        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(params,
                header(MediaType.APPLICATION_FORM_URLENCODED));
        return rest().postForEntity(url, formEntity, tClass);
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> tClass) {
        return rest().getForEntity(url, tClass);
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> tClass, Map<String, String> headersMap) {
        HttpEntity httpEntity;
        HttpHeaders headers = header();
        for (Map.Entry<String, String> entry : headersMap.entrySet()) {
            headers.add(entry.getKey(), entry.getValue());
        }
        httpEntity = new HttpEntity(headers);
        return rest().exchange(url, HttpMethod.GET, httpEntity, tClass);
    }

    public static <T> T doGet(String url, Class<T> tClass) {
        ResponseEntity<String> responseEntity = rest().getForEntity(url, String.class);
        return JSONObject.parseObject(responseEntity.getBody(), tClass);
    }

    public static <T> ResponseEntity<T> doPost(String url, JSONObject params, Class<T> tClass) throws RestClientException {
        HttpEntity httpEntity = new HttpEntity(params, header());
        return rest().exchange(url, HttpMethod.POST, httpEntity, tClass);
    }

    public static <T> ResponseEntity<T> doPost(String url, Object params, Class<T> tClass) {
        HttpEntity<String> formEntity = new HttpEntity<>(JSON.toJSONString(params));
        return rest().postForEntity(url, formEntity, tClass);
    }
}
