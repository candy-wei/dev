package com.ningyuan.intercepter;

import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class NingyuanInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*//jsp不拦截
        if (!request.getRequestURI().endsWith(".jsp")) {
            Context.clearThreadLocal();
        } else {
            return true;
        }

        String promoteType = extractPromoteTypeFromUrI(request);
        if (!Conf.enable(promoteType + ".auth.enable.off")) {
            return true;
        }

        if (!Arrays.asList(Conf.get("promote.access.modules").split(",")).contains(promoteType)) {
//            ResponseUtils.printError("authFailusre", "模块" + promoteType + "未启用");
            return false;
        }*/

        /*String openId = extractOpenIdFromUrI(request);
        IWxRelateService relateServie = Context.getBean(promoteType, IWxRelateService.class);
        Object object = relateServie.getRelateByOpenId(openId);
        if (object == null) {
            ResponseUtils.printError("authFailusre", "未找到openId为" + openId + "的用户");
            return false;
        }*/

        String openId = request.getHeader("openId");
        Context.addTreadLocal("openId", openId);
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Context.clearThreadLocal();
    }

    private String extractPromoteTypeFromUrI(HttpServletRequest request) {
        String path = request.getContextPath();
        //从链接中获得promoteType
        String promoteType = request.getRequestURI().replaceAll(".*" + path + "/(.*?)/.*", "$1");
        Context.addTreadLocal("promoteType", promoteType);
        Context.getHttpServletRequest().setAttribute("promoteType", promoteType);
        return promoteType;
    }

    private String extractOpenIdFromUrI(HttpServletRequest request) {
        //从链接中获得openId
        String uri = request.getRequestURI().replaceAll(";jsessionid(.*?)$", "");
        String openId = uri.replaceAll(".*/(.*?)$", "$1");
        if (StringUtils.isEmpty(openId)) {
            openId = uri.replaceAll(".*/(.*?)/$", "$1");
        }

        //设置线程变量
        Context.addTreadLocal("openId", openId);
        Context.getHttpServletRequest().setAttribute("openId", openId);
        return openId;
    }

}
