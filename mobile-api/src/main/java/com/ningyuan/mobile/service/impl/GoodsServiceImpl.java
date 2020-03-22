package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.GoodsMapper;
import com.ningyuan.mobile.dto.GoodsDto;
import com.ningyuan.mobile.model.ShopGoodsModel;
import com.ningyuan.mobile.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends BaseServiceImpl<GoodsMapper, ShopGoodsModel> implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsDto> getGoods(Long idCategory, Boolean isOnSale) {
        return goodsMapper.getGoods(idCategory, isOnSale);
    }
}
