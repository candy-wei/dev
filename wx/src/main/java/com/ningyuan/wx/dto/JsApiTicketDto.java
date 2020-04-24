package com.ningyuan.wx.dto;

import java.util.Date;

public class JsApiTicketDto {

    private String errcode;
    private String errmsg;
    private String ticket;
    private int expires_in;
    private Date expire = new Date();


    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expire = new Date(expire.getTime() + expires_in * 1000 - 100000);
        this.expires_in = expires_in;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }


    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
