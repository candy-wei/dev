package com.ningyuan.system.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*if(Conf.enable("sys.event.interceptor.enable")) {
            threadPool.execute(() -> {
                String uri = request.getRequestURI().replaceAll("/(.*?)/(.*?)/(.*?)/(.*?)/.*", "$1/$2/$3/$4");
                sysEventStatsService.countUrl(uri);
            });
        }*/
       return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }

}
