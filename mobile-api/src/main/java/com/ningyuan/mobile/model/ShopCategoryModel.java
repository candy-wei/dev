package com.ningyuan.mobile.model;

import javax.persistence.Column;
import com.ningyuan.base.annotation.FiledComment;
import javax.persistence.Table;
import com.ningyuan.base.BaseModel;
import java.util.Date;

/**
 * generated by Generate POJOs.groovy 
 * <p>Date: Wed Mar 18 11:33:46 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Table ( name ="shop_category" )
public class ShopCategoryModel extends BaseModel {

    /**
     * 图标
     */
    @FiledComment(text = "图标" )
    @Column(name = "icon" )
    private String icon;

    /**
     * 名称
     */
    @FiledComment(text = "名称" )
    @Column(name = "name" )
    private String name;

    /**
     * 链接地址
     */
    @FiledComment(text = "链接地址" )
    @Column(name = "url" )
    private String url;

    @Column(name = "created_time" )
    private Date createdTime;

    @Column(name = "update_time" )
    private Date updateTime;

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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