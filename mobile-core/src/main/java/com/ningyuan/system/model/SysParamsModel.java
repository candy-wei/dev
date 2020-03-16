package com.ningyuan.system.model;

import com.ningyuan.base.BaseModel;
import com.ningyuan.base.annotation.FiledComment;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table ( name ="sys_params" )
public class SysParamsModel extends BaseModel {

    @Column(name = "param_type" )
    private String paramType;

    @Column(name = "param_key" )
    private String paramKey;

    @Column(name = "param_value" )
    private String paramValue;

    @Column(name = "update_time" )
    private Date updateTime;

    /**
     * 参数说明
     */
    @FiledComment(text = "参数说明" )
    @Column(name = "param_desc" )
    private String paramDesc;

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamKey() {
        return this.paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getParamDesc() {
        return this.paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

}
