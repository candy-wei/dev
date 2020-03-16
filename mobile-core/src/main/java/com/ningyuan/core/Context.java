package com.ningyuan.core;

import com.ningyuan.security.ISecurity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by eronzen on 11/30/2016.`
 */
public class Context {

    private static final ThreadLocal<Map<String, Object>> treadLocal = new ThreadLocal<Map<String, Object>>();

    public static ServletRequestAttributes getServletRequestAttributes(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    }

    public static HttpServletRequest getHttpServletRequest(){
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getHttpServletResponse(){
        return getServletRequestAttributes().getResponse();
    }

    public static HttpSession getHttpSession(){
        return getHttpServletRequest().getSession();
    }

    public static ServletContext getServletContext(){
        return  getHttpSession().getServletContext();
    }

    public static ApplicationContext getApplicationContext(){
       return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }

    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    public static  <T> T getBean(Class<T> tClass) throws BeansException {
        return getApplicationContext().getBean(tClass);
    }

    public static <T> T getBean(String beanName, Class<T> tClass) {
        try{
            return getApplicationContext().getBean(beanName, tClass);
        }catch (Exception e){
        }
        return null;
    }

    public static <T> Map<String, T> getBeans(Class<T> tClass) {
        try{
            return  getApplicationContext().getBeansOfType(tClass);
        }catch (Exception e){
        }
        return null;
    }

    public static void addTreadLocal(String key, Object o){
        if(treadLocal.get() == null){
            treadLocal.set(new HashMap<String, Object>());
        }

        treadLocal.get().put(key, o);
    }

    public static <T> T getTreadLocal(String key){
        return (T) treadLocal.get().get(key);
    }

    public static <T> T getPromoteType(){
        return getTreadLocal("promoteType");
    }

    public static <T> T getOpenId(){
        return getTreadLocal("openId");
    }

    public static boolean containTreadLocal(String key){
        if(Conf.containsKey("promote.thread.local.enable") && !Boolean.parseBoolean(Conf.get("promote.thread.local.enable"))){
            return false;
        }

        return treadLocal.get() != null && getTreadLocal(key) != null;
    }

    public static ThreadLocal<Map<String, Object>> getTreadLocal() {
        return treadLocal;
    }

    public static void clearThreadLocal() {
        if(treadLocal.get() != null){
            treadLocal.get().clear();
        }
        treadLocal.remove();
    }

    public static <T> T getCurrentUser(Class<T> tClass) {
        try {
            T t = tClass.newInstance();
            BeanUtils.copyProperties(getBean(ISecurity.class).getCurrentUser(), t);
            return t ;
        } catch (Exception e) {
        }
        throw new RuntimeException();
    }
}
