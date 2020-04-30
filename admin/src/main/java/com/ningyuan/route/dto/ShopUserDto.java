package com.ningyuan.route.dto;

public class ShopUserDto {
    private Long id;
    private String openId;
    private String nickname;
    private String receiver;
    private String mobile;
    private String vip;
    private Integer points;
    private Integer redpacketReceive;
    private Integer redpacketAmount;
    private Integer redpacketFinance;
    private Integer redpacketDaily;
    private Boolean newTask;
    private Boolean specialTask;
    private Boolean dailyTask;
    private String createTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getOpenId() { return openId; }

    public void setOpenId(String openId) { this.openId = openId; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getReceiver() { return receiver; }

    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMobile() { return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getVip() { return vip; }

    public void setVip(String vip) { this.vip = vip; }

    public Integer getPoints() { return points; }

    public void setPoints(Integer points) { this.points = points; }

    public Integer getRedpacketReceive() { return redpacketReceive; }

    public void setRedpacketReceive(Integer redpacketReceive) { this.redpacketReceive = redpacketReceive; }

    public Integer getRedpacketAmount() { return redpacketAmount; }

    public void setRedpacketAmount(Integer redpacketAmount) { this.redpacketAmount = redpacketAmount; }

    public Integer getRedpacketFinance() { return redpacketFinance; }

    public void setRedpacketFinance(Integer redpacketFinance) { this.redpacketFinance = redpacketFinance; }

    public Integer getRedpacketDaily() { return redpacketDaily; }

    public void setRedpacketDaily(Integer redpacketDaily) { this.redpacketDaily = redpacketDaily; }

    public String getCreateTime() { return createTime; }

    public void setCreateTime(String createTime) { this.createTime = createTime; }

    public Boolean getNewTask() {
        return this.newTask;
    }

    public void setNewTask(Boolean newTask) {
        this.newTask = newTask;
    }

    public Boolean getSpecialTask() {
        return this.specialTask;
    }

    public void setSpecialTask(Boolean specialTask) {
        this.specialTask = specialTask;
    }

    public Boolean getDailyTask() {
        return this.dailyTask;
    }

    public void setDailyTask(Boolean dailyTask) {
        this.dailyTask = dailyTask;
    }
}
