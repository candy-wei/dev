package com.ningyuan.security.custom.service.impl;

import com.ningyuan.core.Conf;
import com.ningyuan.security.custom.service.CustomFilterSecurityInterceptor;
import com.ningyuan.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by eronzen on 11/15/2016.
 */
@Service("customFilterSecurityInterceptor")
public class CustomFilterSecurityInterceptorImpl extends AbstractSecurityInterceptor implements CustomFilterSecurityInterceptor {

    @Resource
    @Qualifier("customInvocationSecurityMetadataSource")
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Resource
    @Qualifier("customAccessDecisionManager")
    @Override
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        super.setAccessDecisionManager(accessDecisionManager);
    }

    @Resource
    @Qualifier("authenticationManager")
    @Override
    public void setAuthenticationManager(AuthenticationManager newManager) {
        super.setAuthenticationManager(newManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(!Conf.enable("security.authentication.close")){
            if(SecurityUtils.getSecurityContext().getAuthentication() instanceof AnonymousAuthenticationToken){
                throw new AccessDeniedException("Access Denied");
            }
        }

        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }
}
