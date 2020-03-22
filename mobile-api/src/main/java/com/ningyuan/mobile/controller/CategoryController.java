package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.mobile.model.ShopCategoryModel;
import com.ningyuan.mobile.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "首页图片资源加载")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list() {
        log.info("CategoryController.list: {}", "日志日志日志日志日志日志日志日志日志");
        List<ShopCategoryModel> list = categoryService.listCategory();
        list.forEach(item->{
            item.setBannerList(categoryService.listBannerRel(item.getId()));
        });
        return Rets.success(list);
    }
}
