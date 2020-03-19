package com.ningyuan.mobile.dto;

import com.ningyuan.base.BaseModel;
import com.ningyuan.mobile.model.CmsBannerModel;

import java.util.List;

public class CategoryDto extends BaseModel {
    private String name;
    private String url;
    private String icon;
    private List<CmsBannerModel> bannerList;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public List<CmsBannerModel> getBannerList() { return bannerList; }

    public void setBannerList(List<CmsBannerModel> bannerList) { this.bannerList = bannerList; }
}
