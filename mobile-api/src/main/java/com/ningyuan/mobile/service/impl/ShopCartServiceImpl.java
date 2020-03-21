package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.ShopCartMapper;
import com.ningyuan.mobile.dto.ShopCartDto;
import com.ningyuan.mobile.model.ShopCartModel;
import com.ningyuan.mobile.service.IShopCartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Sat Mar 21 14:55:34 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopCartServiceImpl extends BaseServiceImpl<ShopCartMapper, ShopCartModel> implements IShopCartService {


    @Override
    public List<ShopCartDto> queryCart(String openId) {
        return this.mapper.queryCart(openId);
    }
}