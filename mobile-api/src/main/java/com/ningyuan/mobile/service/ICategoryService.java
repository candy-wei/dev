package com.ningyuan.mobile.service;

import com.ningyuan.base.IBaseService;
import com.ningyuan.mobile.dto.CategoryBannerDto;
import com.ningyuan.mobile.dto.CategoryDto;
import com.ningyuan.mobile.model.ShopCategoryModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService extends IBaseService<ShopCategoryModel> {

    List<ShopCategoryModel> listCategory();

    List<CategoryBannerDto> listBannerRel(Long id);
}

