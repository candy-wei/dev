package com.ningyuan.mobile.service.impl;

import com.ningyuan.base.BaseServiceImpl;
import com.ningyuan.mobile.daomapper.mapper.ShopReceiveRecordMapper;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;
import com.ningyuan.mobile.service.IShopReceiveRecordService;
import org.springframework.stereotype.Service;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Fri Apr 03 15:21:29 CST 2020.</p>
 *
 * @author (zengrc)
 */

@Service
public class ShopReceiveRecordServiceImpl extends BaseServiceImpl<ShopReceiveRecordMapper, ShopReceiveRecordModel> implements IShopReceiveRecordService {

    @Override
    public void insertRecord(String optType, String amount, String openId) {
        ShopReceiveRecordModel recordModel = new ShopReceiveRecordModel();
        recordModel.setAmount(amount);
        recordModel.setOpenId(openId);
        recordModel.setOptType(optType);
        this.mapper.insertSelective(recordModel);
    }
}
