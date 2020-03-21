package com.ningyuan.mobile.daomapper.mapper;

import com.ningyuan.mobile.model.CmsBannerModel;
import com.ningyuan.mobile.model.ShopCategoryModel;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<ShopCategoryModel>{

    List<CmsBannerModel> listBannerRel(Long id);
}