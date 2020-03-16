package com.ningyuan.base.datasource;

import com.ningyuan.base.annotation.CacheEnbale;
import com.ningyuan.core.Conf;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.WeakHashMap;

@Aspect
@Order(1)
@Component
public class CacheEnableAspect {
    private static Map<Object, Object> cacheMap = new WeakHashMap<>();

    @Around("within(com.ningyuan.*.service..*) && @annotation(cacheEnbale)")
    public Object cache(ProceedingJoinPoint pjp, CacheEnbale cacheEnbale) throws Throwable {
        Object key = key(pjp);

        if(Conf.enable("promote.cache.enable")){
            if (!cacheMap.containsKey(key)) {
                cacheMap.put(key, pjp.proceed());
            }
            return cacheMap.get(key);

        }
        return pjp.proceed();
    }

    private Object key(ProceedingJoinPoint pjp){
        Signature signature = pjp.getSignature();
        Object key =  signature.toString();
        for(Object o : pjp.getArgs()){
            if(o instanceof String){
                key = ((String) key).concat((String) o);
            }
        }
        return key;
    }

    public static void clear(){
        cacheMap.clear();
    }
}
