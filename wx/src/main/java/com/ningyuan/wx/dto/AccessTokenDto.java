package com.ningyuan.wx.dto;

import java.util.Date;

public class AccessTokenDto {
    private String access_token;
    private int  expires_in;
    private Date expire = new Date();

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expire = new Date(expire.getTime() + expires_in*1000 - 100000);
        this.expires_in = expires_in;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
