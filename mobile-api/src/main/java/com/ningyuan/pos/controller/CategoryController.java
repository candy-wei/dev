package com.ningyuan.pos.controller;

import com.ningyuan.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：enilu
 * @date ：Created in 11/4/2019 9:06 PM
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {

    /*@Autowired
    private ICategoryService categoryService;
    @Autowired
    private CategoryBannerRelService categoryBannerRelService;
    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object list() {
        log.info("CategoryController.list: {}", "日志日志日志日志日志日志日志日志日志");
        List<Category> list = categoryService.queryAll();
        list.forEach(item->{
            List<CategoryBannerRel> relList = categoryBannerRelService.queryAll(SearchFilter.build("idCategory",item.getId()));
            List<Banner> bannerList = Lists.newArrayList();
            relList.forEach( relItem->{
                bannerList.add(relItem.getBanner());
            });

            item.setBannerList(bannerList);
        });
        return Rets.success(list);
    }*/
}
