package com.ningyuan.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

public class BaseModel {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id")
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUpdateTime(Date updateTime){}
}
