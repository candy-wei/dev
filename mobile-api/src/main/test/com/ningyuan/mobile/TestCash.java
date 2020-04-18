package com.ningyuan.mobile;

import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;
import com.ningyuan.mobile.service.IShopReceiveRecordService;
import com.ningyuan.wx.model.WxPay2userResultModel;
import com.ningyuan.wx.utils.AppletUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestCash {
    @Autowired
    private IShopReceiveRecordService recordService;

    @Test
    public void testCash() throws Exception {
        WxPay2userResultModel resultModel = AppletUtils.pay2User(Conf.get("wxsa.appId"), Context.getOpenId()
                , "10", Conf.get("wx.pay2user.desc")
                , Conf.get("shop.cash.reason"));
        if (StringUtils.equals("SUCCESS", resultModel.getResultCode())) {
            ShopReceiveRecordModel recordModel = new ShopReceiveRecordModel();
            recordModel.setAmount("10");
            recordModel.setOpenId(Context.getOpenId());
            recordModel.setOptType(Conf.get("shop.red.packet.type:2"));
            recordService.insertSelective(recordModel);
        }
    }
}
