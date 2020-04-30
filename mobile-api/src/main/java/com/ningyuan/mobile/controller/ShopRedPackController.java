package com.ningyuan.mobile.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.mobile.dto.RedPacketDto;
import com.ningyuan.mobile.model.ShopCustomerModel;
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

import java.util.*;

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
    public String canCashSum() {
        ShopWalletModel walletModel =new ShopWalletModel();
        walletModel.setOpenId(Context.getOpenId());
        walletModel = walletService.selectLimitOne(walletModel);
        return walletModel != null ? walletModel.getFinance() : "0";
    }

    @ApiOperation(value = "总收益")
    @PostMapping("cashSum")
    @ResponseBody
    public String cashSum() {
        String cashSum = walletService.getCashSum();
        return cashSum != null ? cashSum : "0";
    }

    @ApiOperation(value = "领取记录")
    @PostMapping("getList")
    @ResponseBody
    public Map<String, List<RedPacketDto>> getCashList(Integer pageNum, Integer pageSize) {
        CommonUtil.initPageInfo(pageNum, pageSize, Integer.valueOf(Conf.get("theme.getCashList.pageSize:10")));
        Map<String, List<RedPacketDto>> map = new HashMap<>();
        List<RedPacketDto> list = walletService.getCashList(Context.getOpenId());
        List<RedPacketDto> cashList = new ArrayList<>();
        List<RedPacketDto> receiveList = new ArrayList<>();
        List<RedPacketDto> recordList = new ArrayList<>();
        Integer cashtype = Integer.parseInt(Conf.get("wallet.cash.type:2"));
        Integer openType = Integer.parseInt(Conf.get("wallet.open.type:3"));
        Integer receiveType = Integer.parseInt(Conf.get("wallet.receive.type:6"));
        list.forEach(redRacket -> {
            if (redRacket.getOptType().equals(cashtype)) {
                cashList.add(redRacket);
            } else if (redRacket.getOptType().equals(openType) || redRacket.getOptType().equals(receiveType)) {
                receiveList.add(redRacket);
            } else {
                recordList.add(redRacket);
            }
        });
        map.put("cashList", cashList);
        map.put("receiveList", receiveList);
        map.put("recordList", recordList);
        return map;
    }

    @ApiOperation(value = "提现")
    @PostMapping("cash")
    @ResponseBody
    public Object cash() throws Exception{
        ShopWalletModel walletModel =new ShopWalletModel();
        String openId = Context.getOpenId();
        walletModel.setOpenId(openId);
        walletModel = walletService.selectLimitOne(walletModel);
        if (walletModel != null && Double.parseDouble(walletModel.getFinance()) > 0.0) {
            WxPay2userResultModel resultModel = AppletUtils.pay2User(Conf.get("wxsa.appId"), openId
                    , (int)(Double.parseDouble(walletModel.getFinance()) * 100) + "", Conf.get("wx.pay2user.desc")
                    , Conf.get("shop.cash.reason"));
            if (StringUtils.equals("SUCCESS", resultModel.getResultCode())) {
                recordService.insertRecord(Conf.get("shop.cash.type:2"), walletModel.getFinance(), openId);
                walletService.updateWallet(openId);
                return ErrorMessage.getSuccess();
            }
        }
        return ErrorMessage.getFailure();
    }

    @ApiOperation(value = "领取红包")
    @PostMapping("open/redpacket")
    @ResponseBody
    public String openRedpacket() throws StatelessException {
        String openId = Context.getOpenId();
        ShopCustomerModel customerModel = customerService.checkuser(openId);
        if (customerModel == null) {
            throw new StatelessException(ErrorMessage.getFailure());
        }
        String money = customerService.openRedpacket(openId);
        return money;
    }
}
