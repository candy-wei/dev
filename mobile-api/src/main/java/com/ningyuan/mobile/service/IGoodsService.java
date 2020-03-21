package com.ningyuan.mobile.service;

import com.ningyuan.base.IBaseService;
import com.ningyuan.mobile.dto.GoodsDto;
import com.ningyuan.mobile.model.ShopGoodsModel;

import java.util.List;

public interface IGoodsService extends IBaseService<ShopGoodsModel> {
    List<GoodsDto> getGoods(Long idCategory, Boolean isOnSale);
}
