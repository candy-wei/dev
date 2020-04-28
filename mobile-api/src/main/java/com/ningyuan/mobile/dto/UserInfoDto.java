package com.ningyuan.mobile.dto;

public class UserInfoDto {
    private String openId;
    private String nickName;
    private String headImgUrl;
    private String vip;
    private String vipName;
    private Integer points;
    private Integer redpacketAmount;
    private Integer redpacketReceive;
    private Integer redpacketDaily;

    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }

    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getVip() { return vip; }

    public void setVip(String vip) { this.vip = vip; }

    public Integer getPoints() { return points; }

    public void setPoints(Integer points) { this.points = points; }

    public Integer getRedpacketAmount() { return redpacketAmount; }

    public void setRedpacketAmount(Integer redpacketAmount) { this.redpacketAmount = redpacketAmount; }

    public Integer getRedpacketReceive() { return redpacketReceive; }

    public void setRedpacketReceive(Integer redpacketReceive) { this.redpacketReceive = redpacketReceive; }

    public String getHeadImgUrl() { return headImgUrl; }

    public void setHeadImgUrl(String headImgUrl) { this.headImgUrl = headImgUrl; }

    public Integer getRedpacketDaily() { return redpacketDaily; }

    public void setRedpacketDaily(Integer redpacketDaily) { this.redpacketDaily = redpacketDaily; }

    public String getVipName() { return vipName; }

    public void setVipName(String vipName) { this.vipName = vipName; }
}
