package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;
import com.ningyuan.mobile.model.ShopWalletModel;
import com.ningyuan.mobile.service.IShopReceiveRecordService;
import com.ningyuan.mobile.service.IShopWalletService;
import com.ningyuan.utils.CommonUtil;
import com.ningyuan.wx.model.WxPay2userResultModel;
import com.ningyuan.wx.utils.AppletUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("shop/redPack")
public class ShopRedPackController extends BaseController {

    @Autowired
    private IShopWalletService walletService;

    @Autowired
    private IShopReceiveRecordService recordService;

    @ApiOperation(value = "可提现总金额")
    @PostMapping("canCashSum")
    @ResponseBody
    public Object canCashSum() {
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(Context.getOpenId());
        walletModel = walletService.selectLimitOne(walletModel);
        return Integer.parseInt(walletModel.getFinance());
    }

    @ApiOperation(value = "提现记录",notes="hasPost =true:已提现,false:未提现")
    @PostMapping("getCashList")
    @ResponseBody
    public Object getCashList(Integer pageNum, Integer pageSize) {
        CommonUtil.initPageInfo(pageNum, pageSize, Integer.valueOf(Conf.get("theme.getCashList.pageSize:10")));
        return walletService.getCashList(Context.getOpenId());
    }

    @ApiOperation(value = "提现")
    @PostMapping("cash")
    @ResponseBody
    public Object cash() throws Exception{
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(Context.getOpenId());
        walletModel = walletService.selectLimitOne(walletModel);
        if (walletModel != null) {
            WxPay2userResultModel resultModel = AppletUtils.pay2User(Conf.get("wxsa.appId"), Context.getOpenId()
                    , walletModel.getFinance(), Conf.get("wx.pay2user.desc")
                    , Conf.get("shop.cash.reason"));
            if (StringUtils.equals("SUCCESS", resultModel.getResultCode())) {
                ShopReceiveRecordModel recordModel = new ShopReceiveRecordModel();
                recordModel.setAmount("100");
                recordModel.setOpenId(Context.getOpenId());
                recordModel.setType(Conf.get("shop.red.packet.type:2"));
                recordService.insertSelective(recordModel);
            }
        }
        return ErrorMessage.getSuccess();
    }
}
