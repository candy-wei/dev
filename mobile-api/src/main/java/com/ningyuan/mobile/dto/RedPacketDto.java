package com.ningyuan.mobile.dto;

import java.util.Date;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/6 10:58
 */
public class RedPacketDto {
    private String nickName;
    private String headImgUrl;
    private String openId;
    private Date createTime;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() { return headImgUrl; }

    public void setHeadImgUrl(String headImgUrl) { this.headImgUrl = headImgUrl; }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
