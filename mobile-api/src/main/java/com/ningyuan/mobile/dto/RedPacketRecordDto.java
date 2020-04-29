package com.ningyuan.mobile.dto;

import java.util.Date;

public class RedPacketRecordDto {
    private String redpacketSum;
    private Integer num;
    private Date createTime;

    public String getRedpacketSum() { return redpacketSum; }

    public void setRedpacketSum(String redpacketSum) { this.redpacketSum = redpacketSum; }

    public Integer getNum() { return num; }

    public void setNum(Integer num) { this.num = num; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
