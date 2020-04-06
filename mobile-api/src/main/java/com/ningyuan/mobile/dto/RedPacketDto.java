package com.ningyuan.mobile.dto;

import java.util.Date;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/6 10:58
 */
public class RedPacketDto {
    private String optionType;
    private String finance;
    private String openId;
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
