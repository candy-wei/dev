package com.ningyuan.wx.dto;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 16:43 on 2018/5/31.
 * @Modified by:
 */
public class JsApiDto {

    private String appId;
    private String timestamp;
    private String noncestr;
    private String signature;

    public JsApiDto(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
