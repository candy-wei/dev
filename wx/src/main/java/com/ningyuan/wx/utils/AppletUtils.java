package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSONObject;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.wx.dto.Pay2UserDto;
import com.ningyuan.wx.model.WxPay2userResultModel;
import com.ningyuan.wx.service.IWxPay2userResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

public class AppletUtils {

    private static final Logger log = LoggerFactory.getLogger(AppletUtils.class);

    public static WxPay2userResultModel pay2User(String mchAppId, String openId, String amount, String desc,
                                                 String reason) throws Exception {
        IWxPay2userResultService resultService = Context.getBean(IWxPay2userResultService.class);
        WxPay2userResultModel sel =new WxPay2userResultModel();
        sel.setOpenId(openId);
        sel.setReason(reason);
        WxPay2userResultModel model = resultService.selectOne(sel);
        Pay2UserDto pay2UserDto = new Pay2UserDto();
        pay2UserDto.setMch_appid(mchAppId);
        pay2UserDto.setMchid(Conf.get("wx.partner"));
        pay2UserDto.setNonce_str(WxUtils.getMchBillNo());
        pay2UserDto.setOpenid(openId);
        pay2UserDto.setCheck_name("NO_CHECK");
//        pay2UserDto.setRe_user_name(); //非必需
        pay2UserDto.setAmount(amount); //单位:分
        pay2UserDto.setDesc(desc);
        if(model== null){
            pay2UserDto.setPartner_trade_no(WxUtils.getMchBillNo());
            model = new WxPay2userResultModel();
            model.setOpenId(openId);
            model.setReason(reason);
            model.setAmount(pay2UserDto.getAmount());
            model.setResDesc(pay2UserDto.getDesc());
            model.setMchAppid(pay2UserDto.getMch_appid());
            model.setPartnerTradeNo(pay2UserDto.getPartner_trade_no());
            resultService.insertSelective(model);
        }else{
            //发送失败的用原来的商户订单重新发送
            log.info("发送失败了AppletUtils:pay2User");
            pay2UserDto.setPartner_trade_no(model.getPartnerTradeNo());
        }

        if(Conf.enable("theme.pay2user.enable")) {  //测试时 可以把theme.pay2user.enable配置为false
            JSONObject o = WxCertUtils.postWithCert(Conf.get("applet.wx.pay2user.url")
                    , Conf.get("wx.redpack.cert")
                    , pay2UserDto, JSONObject.class);
            model.setResultText(o.toJSONString());
            model.setResultCode(o.getString("result_code"));
            model.setErrCode(o.getString("err_code"));
        }else{
            model.setResultText("test_");
            model.setResultCode("SUCCESS");
            model.setErrCode("test_");
        }
        model.setSuccessTime(new Date());
        resultService.updateByPrimaryKeySelective(model);
        return model;
    }
}
