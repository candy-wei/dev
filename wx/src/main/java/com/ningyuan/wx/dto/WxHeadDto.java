package com.ningyuan.wx.dto;

/**
 * generated by Generate POJOs.groovy
 * <p>Date: Wed Oct 24 09:50:21 CST 2018.</p>
 *
 * @author (zengrc)
 */

public class WxHeadDto {

    private String openId;

    private String nickname;

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    private String headimgurl;

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }
}
