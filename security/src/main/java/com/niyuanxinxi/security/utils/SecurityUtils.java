package com.niyuanxinxi.security.utils;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by eronzen on 12/1/2016.
 */
public class SecurityUtils {

    public static SecurityContext getSecurityContext(){
        return SecurityContextHolder.getContext();
    }

    public static Object getCurrentUser(){
        return getSecurityContext().getAuthentication().getPrincipal();
    }
}
