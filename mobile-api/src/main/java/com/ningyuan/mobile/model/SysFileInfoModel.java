package com.ningyuan.mobile.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ningyuan.base.BaseModel;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Wed Mar 18 11:33:46 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="sys_file_info" )
public class SysFileInfoModel extends BaseModel {

    @Column(name = "original_file_name" )
    private String originalFileName;

    @Column(name = "real_file_name" )
    private String realFileName;

    @Column(name = "create_time" )
    private Date createTime;

    @Column(name = "update_time" )
    private Date updateTime;

    @Transient
    private String ablatePath;

    public String getOriginalFileName() {
        return this.originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getRealFileName() {
        return this.realFileName;
    }

    public void setRealFileName(String realFileName) {
        this.realFileName = realFileName;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAblatePath() { return ablatePath; }

    public void setAblatePath(String ablatePath) { this.ablatePath = ablatePath; }
}
