package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ningyuan.wx.dto.*;
import com.ningyuan.wx.model.WxMsgResultModel;
import com.ningyuan.wx.model.WxPay2userResultModel;
import com.ningyuan.wx.service.IWxMsgResultService;
import com.ningyuan.wx.service.IWxPay2userResultService;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.RESTUtils;
import com.ningyuan.utils.TemplateUtils;
import org.apache.poi.util.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 10:17 on 2018/3/9.
 * @Modified by: czr
 */
public class AppletUtils {

    private static final Logger log = LoggerFactory.getLogger(AppletUtils.class);

    private static AccessTokenDto accessTokenDto = null;

    private static AccessTokenDto getAccessToken(String appId,String appSecret) {
        if (accessTokenDto == null || accessTokenDto.getExpire().before(new Date())) {
            String url = TemplateUtils.replaceAll(Conf.get("applet.accesstoken.url"), appId, appSecret);
            accessTokenDto = RESTUtils.doGet(url, AccessTokenDto.class);
        }
        log.error("accessTokenDto:{}", JSON.toJSONString(accessTokenDto));
        return accessTokenDto;
    }

    public static void getAppletQr(JSONObject params,String appId,String appSecret,  HttpServletResponse response) throws IOException {
        String token = getAccessToken(appId, appSecret).getAccess_token();
        String url = TemplateUtils.replaceAll(Conf.get("applet.wxacode.url"), token);
        ResponseEntity<byte[]> responseEntity = AppletUtils.genAppletQrCode(url, params);
        byte[] bytes = responseEntity.getBody();
        try {
            JSONObject errJson = JSON.parseObject(new String(bytes));
            log.info("errcode：{}，errmsg：{}", errJson.get("errcode"), errJson.get("errmsg"));
            if ("40001".equals(Integer.toString((Integer)errJson.get("errcode")))) {
                accessTokenDto = null;
                token = getAccessToken(appId, appSecret).getAccess_token();
                url = TemplateUtils.replaceAll(Conf.get("applet.wxacode.url"), token);
                bytes = AppletUtils.genAppletQrCode(url, params).getBody();
            }
        } catch (JSONException e) {
        }finally {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    /**
     * 解密数据获取微信绑定手机号
     *
     * @param iv
     * @param encryptedData 数据源
     * @param sessionKey
     * @return
     */
    public static WxGetPhoneDto getAppletNumber(String encryptedData, String iv, String sessionKey) throws Exception {
        byte[] encryptedDataBytes = Base64.decode(encryptedData.getBytes());
        byte[] ivBytes = Base64.decode(iv.getBytes());
        byte[] sessionKeyBytes = Base64.decode(sessionKey.getBytes());
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(sessionKeyBytes, "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] resultButys = cipher.doFinal(encryptedDataBytes);
        String result = new String(resultButys, StandardCharsets.UTF_8);
        return JSON.parseObject(result).toJavaObject(WxGetPhoneDto.class);
    }


    public static ResponseEntity<byte[]> genAppletQrCode(String url, JSONObject params) throws RestClientException {
        ResponseEntity<byte[]> responseEntity = RESTUtils.doPost(url, params, byte[].class);
        return responseEntity;
    }

    public static <T> T postWithToken(String uri, Object params, Class<T> tClass, String appId, String appSecret) {
        AccessTokenDto accessTokenDto = getAccessToken(appId,appSecret);
        String url = TemplateUtils.replaceAll(uri, accessTokenDto.getAccess_token());
        JSONObject object = RESTUtils.doPost(url, params, JSONObject.class).getBody();
        if (!Integer.valueOf(0).equals(object.getInteger("errcode"))) {
            AppletUtils.accessTokenDto = null;
            accessTokenDto = getAccessToken(appId,appSecret);
            url = TemplateUtils.replaceAll(uri, accessTokenDto.getAccess_token());
            object = RESTUtils.doPost(url, params, JSONObject.class).getBody();
        }
        return object.toJavaObject(tClass);
    }

    public static WxMsgResponseDto sendMassage(String type, WxMsgDto msgDto, String appId, String appSecret, String promoteType) {
        WxMsgResultModel resultModel = new WxMsgResultModel();
        resultModel.setPromoteType(promoteType);
        resultModel.setType(type);
        resultModel.setOpenId(msgDto.getTouser());
        resultModel.setFormId(msgDto.getForm_id());
        resultModel.setTemplateId(msgDto.getTemplate_id());
        resultModel.setData(JSON.toJSONString(msgDto.getData()));
        resultModel.setEmphasisKeyword(msgDto.getEmphasis_keyword());
        resultModel.setPage(msgDto.getPage());
        IWxMsgResultService resultService = Context.getBean(IWxMsgResultService.class);
        resultService.insertSelective(resultModel);
        WxMsgResponseDto responseDto = AppletUtils.postWithToken(Conf.get("weixin.msg.model.url"), msgDto,
                WxMsgResponseDto.class,
                appId, appSecret);
        resultModel.setErrCode(responseDto.getErrcode());
        resultModel.setErrMsg(responseDto.getErrmsg());
        resultModel.setSuccessTime(new Date());
        resultService.updateByPrimaryKeySelective(resultModel);
        return responseDto;
    }

    public static WxPay2userResultModel pay2User(String mchAppId, String openId, String amount, String desc,
                                                 String reason, String promoteType) throws Exception {
        IWxPay2userResultService resultService = Context.getBean(IWxPay2userResultService.class);
        WxPay2userResultModel sel =new WxPay2userResultModel();
        sel.setOpenId(openId);
        sel.setReason(reason);
        sel.setPromoteType(promoteType);
        WxPay2userResultModel model = resultService.selectOne(sel);
        if (model != null && "SUCCESS".equals(model.getResultCode())) {
            throw new StatelessException(ErrorMessage.getFailure("redPack_again", "您已领取红包"));
        }
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
            model.setPromoteType(promoteType);
            model.setAmount(pay2UserDto.getAmount());
            model.setResDesc(pay2UserDto.getDesc());
            model.setMchAppid(pay2UserDto.getMch_appid());
            model.setPartnerTradeNo(pay2UserDto.getPartner_trade_no());
            resultService.insertSelective(model);
        }else{
            //发送失败的用原来的商户订单重新发送
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

    public static GetTokenByCodeResultDto getAppletOpenId(String appId, String appSecret, String jsCode) {
        String url = TemplateUtils.replaceAll(Conf.get("applet.getOpenId.url"), appId, appSecret, jsCode);
        return RESTUtils.doGet(url, GetTokenByCodeResultDto.class);
    }

    /**
     * 发送小程序订阅消息
     *
     * @param appId
     * @param appSecret
     * @param wxSubscribeMessageDto
     * @return
     */
    public static WxMsgResponseDto sendWxSubscribeMessage(String appId, String appSecret, WxSubscribeMessageDto wxSubscribeMessageDto) {
        return postWithToken(Conf.get("wx.msg.subscribe.url"), wxSubscribeMessageDto, WxMsgResponseDto.class, appId, appSecret);
    }

}
