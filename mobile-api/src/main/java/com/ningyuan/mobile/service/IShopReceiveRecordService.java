package com.ningyuan.mobile.service;

import com.ningyuan.base.IBaseService;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;

/**
 * generated by Generate Service.groovy 
 * <p>Date: Fri Apr 03 15:21:29 CST 2020.</p>
 *
 * @author (zengrc)
 */

public interface IShopReceiveRecordService extends IBaseService<ShopReceiveRecordModel> {

    void insertRecord(String optType, String amount, String openId);
}
