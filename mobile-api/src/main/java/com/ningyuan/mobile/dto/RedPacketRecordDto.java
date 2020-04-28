package com.ningyuan.mobile.dto;

import java.util.Date;

public class RedPacketRecordDto {
    private String desc;
    private Integer num;
    private Date createTime;

    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    public Integer getNum() { return num; }

    public void setNum(Integer num) { this.num = num; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
