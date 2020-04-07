package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.dto.RedPacketDto;
import com.ningyuan.mobile.model.ShopCustomerModel;
import com.ningyuan.mobile.model.ShopReceiveRecordModel;
import com.ningyuan.mobile.model.ShopWalletModel;
import com.ningyuan.mobile.service.IShopCustomerService;
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

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("shop/redPack")
public class ShopRedPackController extends BaseController {

    @Autowired
    private IShopWalletService walletService;

    @Autowired
    private IShopReceiveRecordService recordService;

    @Autowired
    private IShopCustomerService customerService;

    @ApiOperation(value = "可提现总金额")
    @PostMapping("canCashSum")
    @ResponseBody
    public Integer canCashSum() {
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(Context.getOpenId());
        walletModel = walletService.selectLimitOne(walletModel);
        return Integer.parseInt(walletModel.getFinance());
    }

    @ApiOperation(value = "总收益")
    @PostMapping("cashSum")
    @ResponseBody
    public String cashSum() {
        return walletService.getCashSum();
    }

    @ApiOperation(value = "提现记录")
    @PostMapping("getCashList")
    @ResponseBody
    public List<RedPacketDto> getCashList(Integer pageNum, Integer pageSize) {
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
                recordModel.setOptType(Conf.get("shop.red.packet.type:2"));
                recordService.insertSelective(recordModel);
            }
        }
        return ErrorMessage.getSuccess();
    }

    @ApiOperation(value = "领取红包")
    @PostMapping("open/redpacket")
    @ResponseBody
    public String openRedpacket() throws StatelessException {
        String openId = Context.getOpenId();
        ShopCustomerModel customerModel = customerService.checkuser(openId);
        if (customerModel == null || customerModel.getRedpacketReceive() < 0 || customerModel.getRedpacketFinance().compareTo(BigDecimal.ZERO) < 0) {
            throw new StatelessException(ErrorMessage.getFailure());
        }
        String money = customerService.openRedpacket(openId);
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(openId);
        ShopWalletModel existModel = walletService.selectLimitOne(walletModel);
        if (existModel == null) {
            walletModel.setFinance(money);
            walletService.insertSelective(walletModel);
        }else {
            double sumFinance = add(existModel.getFinance(), money);
            walletModel.setFinance(sumFinance + "");
            walletService.updateByPrimaryKeySelective(existModel);
        }
        return money;
    }

    // 提供精确的减法运算。
    private double add(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).doubleValue();
    }
}
