package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.ShopAddressMapper;
import com.ningyuan.mobile.model.ShopAddressModel;
import com.ningyuan.mobile.service.IShopAddressService;
import org.springframework.stereotype.Service;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Sat Mar 21 14:55:34 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopAddressServiceImpl extends BaseServiceImpl<ShopAddressMapper, ShopAddressModel> implements IShopAddressService {


    @Override
    public ShopAddressModel getDefaultAddr(String openId) {
        ShopAddressModel addressModel = new ShopAddressModel();
        addressModel.setOpenId(openId);
        addressModel.setIsDefault(Boolean.TRUE);
        return this.selectLimitOne(addressModel);
    }
}
