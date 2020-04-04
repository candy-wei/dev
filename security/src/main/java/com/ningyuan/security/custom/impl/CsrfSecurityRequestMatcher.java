package com.ningyuan.security.custom.impl;

import com.ningyuan.core.Conf;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Created by james on 10/17/2017.
 */
@Component
public class CsrfSecurityRequestMatcher implements RequestMatcher {
    private static Pattern allowedMethods;
    private static RegexRequestMatcher unprotectedMatcher;

    static{
        if(Conf.containsKey("security.csrf.allowed.methods")){
            allowedMethods = Pattern.compile("^(" + Conf.get("security.csrf.allowed.methods")+ ")$");
        }
        if(Conf.containsKey("security.csrf.ignore.pattern")){
            unprotectedMatcher =  new RegexRequestMatcher(Conf.get("security.csrf.ignore.pattern"), null);
        }
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if(allowedMethods != null && allowedMethods.matcher(request.getMethod()).matches()){
            return false;
        }

        if(unprotectedMatcher != null){
            return !unprotectedMatcher.matches(request);
        }
       return true;
    }

}
