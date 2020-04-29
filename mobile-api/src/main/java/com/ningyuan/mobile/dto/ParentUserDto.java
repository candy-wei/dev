package com.ningyuan.mobile.dto;

public class ParentUserDto {
    private String parentOpenId;
    private String topParentOpenId;
    private String performanceRatio;
    private String dividendRatio;

    public String getParentOpenId() {
        return parentOpenId;
    }

    public void setParentOpenId(String parentOpenId) {
        this.parentOpenId = parentOpenId;
    }

    public String getTopParentOpenId() {
        return topParentOpenId;
    }

    public void setTopParentOpenId(String topParentOpenId) {
        this.topParentOpenId = topParentOpenId;
    }

    public String getPerformanceRatio() {
        return performanceRatio;
    }

    public void setPerformanceRatio(String performanceRatio) {
        this.performanceRatio = performanceRatio;
    }

    public String getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(String dividendRatio) {
        this.dividendRatio = dividendRatio;
    }
}
