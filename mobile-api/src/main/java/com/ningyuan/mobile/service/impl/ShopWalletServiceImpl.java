package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.ShopWalletMapper;
import com.ningyuan.mobile.dto.RedPacketDto;
import com.ningyuan.mobile.model.ShopWalletModel;
import com.ningyuan.mobile.service.IShopWalletService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Fri Apr 03 14:41:01 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopWalletServiceImpl extends BaseServiceImpl<ShopWalletMapper, ShopWalletModel> implements IShopWalletService {

    @Override
    public List<RedPacketDto> getCashList(String openId) {
        return this.mapper.getCashList(openId);
    }
}