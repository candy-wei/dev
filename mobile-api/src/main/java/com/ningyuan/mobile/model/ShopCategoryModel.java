package com.ningyuan.mobile.model;

import javax.persistence.Column;
import com.ningyuan.base.annotation.FiledComment;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ningyuan.base.BaseModel;
import java.util.Date;
import java.util.List;

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

    @Transient
    private List<CmsBannerModel> bannerList;

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

    public List<CmsBannerModel> getBannerList() { return bannerList; }

    public void setBannerList(List<CmsBannerModel> bannerList) { this.bannerList = bannerList; }
}
