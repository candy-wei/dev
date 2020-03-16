package com.ningyuan.base;

import javax.persistence.Column;

public class BaseRelateModel extends BaseModel{

    /**
     * 转介绍, 介绍人 学员id
     */
    @Column(name = "student_id" )
    protected String studentId;

    /**
     * 业务员id
     */
    @Column(name = "counterman_id" )
    protected String countermanId;

    /**
     * 来源id
     */
    @Column(name = "source_id" )
    protected String sourceId;

    @Column(name = "parent_open_id" )
    protected String parentOpenId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCountermanId() {
        return countermanId;
    }

    public void setCountermanId(String countermanId) {
        this.countermanId = countermanId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getParentOpenId() {
        return parentOpenId;
    }

    public void setParentOpenId(String parentOpenId) {
        this.parentOpenId = parentOpenId;
    }

    public String getOpenId() {return null;}

}
