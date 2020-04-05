package com.ningyuan.security.custom.service.impl;

import com.ningyuan.route.model.BgRoleModel;
import com.ningyuan.route.model.BgUserModel;
import com.ningyuan.route.service.IBgRoleService;
import com.ningyuan.route.service.IBgUserService;
import com.ningyuan.security.custom.service.CustomUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by eronzen on 11/15/2016.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Autowired
    private IBgUserService userService;

    @Autowired
    private IBgRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        BgUserModel user = userService.getUserByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException(username + "is not exist");
        }

        Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<BgRoleModel> roles = roleService.getRolesByUser(user.getId());
        for(BgRoleModel role : roles){
            auths.add(new GrantedAuthorityImpl(role.getName()));
        }

        UserDetailsImpl userDetails = new UserDetailsImpl();
        BeanUtils.copyProperties(user, userDetails);
        userDetails.setAuthorities(auths);
        return userDetails;
    }
}
