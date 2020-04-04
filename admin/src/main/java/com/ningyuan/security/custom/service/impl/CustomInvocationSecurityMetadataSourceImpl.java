package com.ningyuan.security.custom.service.impl;

import com.ningyuan.route.model.BgResourceModel;
import com.ningyuan.security.custom.service.CustomInvocationSecurityMetadataSource;
import com.ningyuan.route.model.BgRoleModel;
import com.ningyuan.route.service.IBgResourceService;
import com.ningyuan.route.service.IBgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by eronzen on 11/15/2016.
 */
@Service("customInvocationSecurityMetadataSource")
public class CustomInvocationSecurityMetadataSourceImpl implements CustomInvocationSecurityMetadataSource {

    private static final HashMap<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

    @Autowired
    private IBgResourceService resourceService;

    @Autowired
    private IBgRoleService roleService;

    @PostConstruct
    public void init() {
        loadResourceDefine();
    }

    private void loadResourceDefine() {

        List<BgRoleModel> roles = roleService.selectAll();

        for (BgRoleModel role : roles) {
            List<BgResourceModel> resources = resourceService.getResourcesByRole(role.getId());
            for (BgResourceModel resource : resources) {
                Collection<ConfigAttribute> configAttributes;
                if (resourceMap.containsKey(resource.getResource())) {
                    configAttributes = resourceMap.get(resource.getResource());
                } else {
                    configAttributes = new ArrayList<>();
                    resourceMap.put(resource.getResource(), configAttributes);
                }
                configAttributes.add(new SecurityConfig(role.getName()));
            }
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String url = ((FilterInvocation) o).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf(".");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }

        Iterator<String> ite = resourceMap.keySet().iterator();
        //取到请求的URL后与上面取出来的资源做比较
        PathMatcher pathMatcher = new AntPathMatcher();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (pathMatcher.match(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
