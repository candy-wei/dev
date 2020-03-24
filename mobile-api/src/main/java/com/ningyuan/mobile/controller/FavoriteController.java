package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.bean.front.Rets;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.model.ShopFavoriteModel;
import com.ningyuan.mobile.service.IShopFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/favorite")
public class FavoriteController extends BaseController {
    @Autowired
    private IShopFavoriteService favoriteService;

    @RequestMapping(value = "/add/{idGoods}",method = RequestMethod.POST)
    public Object add(@PathVariable("idGoods") Long idGoods){
        String openId = Context.getOpenId();
        ShopFavoriteModel model = new ShopFavoriteModel();
        model.setOpenId(openId);
        model.setIdGoods(idGoods);
        ShopFavoriteModel old = favoriteService.selectLimitOne(model);
        if(old!=null){
            return Rets.success();
        }
        ShopFavoriteModel favorite = new ShopFavoriteModel();
        favorite.setOpenId(openId);
        favorite.setIdGoods(idGoods);
        favoriteService.insert(favorite);
        return Rets.success();
    }
    @RequestMapping(value = "/ifLike/{idGoods}",method = RequestMethod.GET)
    public Object ifLike(@PathVariable("idGoods") Long idGoods){
        String openId = Context.getOpenId();
        ShopFavoriteModel model = new ShopFavoriteModel();
        model.setOpenId(openId);
        model.setIdGoods(idGoods);
        ShopFavoriteModel favorite = favoriteService.selectLimitOne(model);
        return Rets.success(favorite!=null);
    }

}
