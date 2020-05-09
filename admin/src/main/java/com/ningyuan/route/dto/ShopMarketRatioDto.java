package com.ningyuan.route.dto;

public class ShopMarketRatioDto {
    private Long id;
    private String vipKey;
    private String vipName;
    private String dividendRatio;
    private String performanceRatio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVipKey() {
        return vipKey;
    }

    public void setVipKey(String vipKey) {
        this.vipKey = vipKey;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(String dividendRatio) {
        this.dividendRatio = dividendRatio;
    }

    public String getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(String performanceRatio) {
        this.performanceRatio = performanceRatio;
    }
}
