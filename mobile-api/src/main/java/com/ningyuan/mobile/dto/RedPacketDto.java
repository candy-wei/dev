package com.ningyuan.mobile.dto;

public class RedPacketDto {
    private String optionType;
    private String finance;
    private String openId;
    private Integer optType;
    private String createTime;

    public String getFinance() { return finance; }

    public void setFinance(String finance) { this.finance = finance; }

    public String getOptionType() { return optionType; }

    public void setOptionType(String optionType) { this.optionType = optionType; }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
