package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.CategoryMapper;
import com.ningyuan.mobile.dto.CategoryBannerDto;
import com.ningyuan.mobile.dto.CategoryDto;
import com.ningyuan.mobile.model.ShopCategoryModel;
import com.ningyuan.mobile.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, ShopCategoryModel> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<ShopCategoryModel> listCategory() {
        return this.mapper.selectAll();
    }

    @Override
    public List<CategoryBannerDto> listBannerRel(Long id) {
        return categoryMapper.listBannerRel(id);
    }
}

