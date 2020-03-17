package com.ningyuan.mobile.model;

import com.ningyuan.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;

@Table( name ="sys_params" )
public class Category extends BaseModel {
    @Column(columnDefinition = "VARCHAR(16) COMMENT '名称'")
    private String name;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '链接地址'")
    private String url;
    @Column(columnDefinition = "VARCHAR(32) COMMENT '图标'")
    private String icon;

}
