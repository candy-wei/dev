package com.niyuanxinxi.security.custom.impl;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by eronzen on 11/16/2016.
 */
public class GrantedAuthorityImpl implements GrantedAuthority {

    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
