package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.mobile.service.ICategoryBannerRelService;
import com.ningyuan.mobile.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ICategoryBannerRelService categoryBannerRelService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list() {
        /*log.info("CategoryController.list: {}", "日志日志日志日志日志日志日志日志日志");
        List<Category> list = categoryService.query();
        list.forEach(item->{
            List<CategoryBannerRel> relList = categoryBannerRelService.queryAll(SearchFilter.build("idCategory",item.getId()));
            List<Banner> bannerList = Lists.newArrayList();
            relList.forEach( relItem->{
                bannerList.add(relItem.getBanner());
            });

            item.setBannerList(bannerList);
        });
        return Rets.success(list);*/
        return null;
    }
}
