package com.ningyuan.mobile.dto;

import com.ningyuan.base.BaseModel;
import com.ningyuan.mobile.model.CmsBannerModel;
import com.ningyuan.mobile.model.ShopCategoryModel;

public class CategoryBannerDto extends BaseModel {
    private Long idCategory;
    private Long idBanner;
    private ShopCategoryModel category;
    private CmsBannerModel banner;

    public Long getIdCategory() { return idCategory; }

    public void setIdCategory(Long idCategory) { this.idCategory = idCategory; }

    public Long getIdBanner() { return idBanner; }

    public void setIdBanner(Long idBanner) { this.idBanner = idBanner; }

    public ShopCategoryModel getCategory() { return category; }

    public void setCategory(ShopCategoryModel category) { this.category = category; }

    public CmsBannerModel getBanner() { return banner; }

    public void setBanner(CmsBannerModel banner) { this.banner = banner; }
}
