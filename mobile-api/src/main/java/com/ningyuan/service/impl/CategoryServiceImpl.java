package com.ningyuan.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.daomapper.mapper.CategoryMapper;
import com.ningyuan.service.ICategoryService;
import com.ningyuan.system.model.SysParamsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<CategoryMapper, SysParamsModel> implements ICategoryService {

    @Autowired
    private CategoryMapper mapper;

}

