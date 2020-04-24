package com.ningyuan.intercepter;

import com.ningyuan.core.Context;
import com.ningyuan.utils.ResponseUtils;
import com.ningyuan.wx.service.IWxRelateService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NingyuanInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // jsp不拦截
        if (!request.getRequestURI().endsWith(".jsp")) {
            Context.clearThreadLocal();
        } else {
            return true;
        }

        String openId = request.getHeader("openId");
        if (StringUtils.isEmpty(openId)) {
            openId = extractOpenIdFromUrI(request);
        }
        IWxRelateService relateServie = Context.getBean("wx", IWxRelateService.class);
        Object object = relateServie.getRelateByOpenId(openId);
        if (object == null) {
            ResponseUtils.printError("authFailusre", "未找到openId为" + openId + "的用户");
            return false;
        }

        Context.addTreadLocal("openId", openId);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Context.clearThreadLocal();
    }

    private String extractOpenIdFromUrI(HttpServletRequest request) {
        //从链接中获得openId
        String uri = request.getRequestURI().replaceAll(";jsessionid(.*?)$", "");
        String openId = uri.replaceAll(".*/(.*?)$", "$1");
        if (org.apache.commons.lang.StringUtils.isEmpty(openId)) {
            openId = uri.replaceAll(".*/(.*?)/$", "$1");
        }

        Context.getHttpServletRequest().setAttribute("openId", openId);
        return openId;
    }

}
