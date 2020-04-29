package com.ningyuan.mobile.dto;

public class ShopRedpacketDto {
    private Long id;
    private Integer remainSize;
    private Integer remainAmount;
    private String remainMoney;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getRemainSize() { return remainSize; }

    public void setRemainSize(Integer remainSize) { this.remainSize = remainSize; }

    public Integer getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(Integer remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getRemainMoney() { return remainMoney; }

    public void setRemainMoney(String remainMoney) { this.remainMoney = remainMoney; }
}
