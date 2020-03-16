package com.ningyuan.system.model;

import com.ningyuan.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table ( name ="sys_event_stats" )
public class SysEventStatsModel extends BaseModel {

    @Column(name = "event_name" )
    private String eventName;

    @Column(name = "count_num" )
    private Long countNum;

    @Column(name = "remark" )
    private String remark;

    @Column(name = "created_time" )
    private Date createdTime;

    @Column(name = "update_time" )
    private Date updateTime;

    public String getEventName() {
        return this.eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getCountNum() {
        return this.countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
