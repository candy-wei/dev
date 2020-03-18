package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ningyuan.wx.dto.*;
import com.ningyuan.wx.model.WxRedPackResultModel;
import com.ningyuan.wx.model.WxUserModel;
import com.ningyuan.wx.service.IWxRedPackResultService;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.*;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 9:58 on 2017/12/4.
 * @Modified by:
 */
public class WxUtils {

    protected static Logger log = LoggerFactory.getLogger(WxUtils.class);

    private static AccessTokenDto accessTokenDto = null;

    private static JsApiTicketDto jsApiTicketDto = null;

    public static <T> T post(String url, Object params, Class<T> tClass) {
        HttpEntity httpEntity = new HttpEntity(params, RESTUtils.header());
        ResponseEntity<String> responseEntity = RESTUtils.rest().postForEntity(url, httpEntity, String.class);
        return JSON.parseObject(responseEntity.getBody()).toJavaObject(tClass);
    }

    public static <T> T get(String url, Class<T> tClass) {
        ResponseEntity<String> responseEntity = RESTUtils.rest().getForEntity(url, String.class);
        return JSON.parseObject(responseEntity.getBody()).toJavaObject(tClass);
    }

    private static AccessTokenDto getAccessToken() {
        accessTokenDto = new AccessTokenDto();
        try {
            accessTokenDto.setAccess_token(Context.getBean(WxMpService.class).getAccessToken());
            accessTokenDto.setExpire(new Date());
            accessTokenDto.setExpires_in(0);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return accessTokenDto;
    }

    public static <T> T postWithToken(String url, Object params, Class<T> tClass) {
        AccessTokenDto accessTokenDto = getAccessToken();
        url = TemplateUtils.replaceAll(url, accessTokenDto.getAccess_token());
        JSONObject object = post(url, params, JSONObject.class);
        if (!object.get("errmsg").equals("OK")) {
            WxUtils.accessTokenDto = null;
            accessTokenDto = getAccessToken();
            url = TemplateUtils.replaceAll(url, accessTokenDto.getAccess_token());
            return post(url, params, tClass);
        }
        return object.toJavaObject(tClass);
    }

    public static GetBrandWCPayRequestDto getPayRequestDto(String promoteType, String attach, String openId, String price) throws Exception {
        return getPayRequestDto(promoteType, attach, openId, price, null);
    }

    /**
     * @param promoteType
     * @param attach
     * @param openId
     * @param price
     * @param payType     类别:"applet"为小程序,否则为公众号
     * @return
     * @throws Exception
     */
    public static GetBrandWCPayRequestDto getPayRequestDto(String promoteType, String attach, String openId, String price, String payType) throws Exception {
        UnifiedorderDto dto = new UnifiedorderDto();
        GetBrandWCPayRequestDto payRequestDto = new GetBrandWCPayRequestDto();
        dto.setAppid(Conf.get("wx.appId"));
        if (org.apache.commons.lang3.StringUtils.equals(payType, "applet")) {
            //小程序
            dto.setAppid(Conf.get("wx.applet.appId"));
        }
        dto.setMch_id(Conf.get("wx.partner"));
        dto.setNonce_str(getNonceStr());
        dto.setBody(Conf.get("wx.pay.body"));
        dto.setFee_type("CNY");
        dto.setAttach(attach);
        dto.setOut_trade_no(CreateGUID.createGuId());
        dto.setTotal_fee((int) (100 * Double.parseDouble(price)) + "");
        dto.setTrade_type("JSAPI");
        dto.setNotify_url(TemplateUtils.replaceAll(Conf.get("wx.notify.uri"), promoteType));
        dto.setOpenid(openId);
        dto.setSpbill_create_ip("127.0.0.1");
        Map<String, String> result = getPrepareId(dto);
        payRequestDto.setAppId(dto.getAppid());
        payRequestDto.setTimeStamp(new Date().getTime() + "");
        payRequestDto.setNonceStr(getNonceStr());
        payRequestDto.setSignType(WXPayConstants.MD5);
        Map params = CommonUtil.objectToMap(payRequestDto);
        params.put("package", "prepay_id=".concat(result.get("prepay_id")));
        payRequestDto.setPaySign(WXPayUtil.generateSignature(params, Conf.get("wx.api.key")));
        payRequestDto.setPrepayId(result.get("prepay_id"));
        return payRequestDto;
    }

    public static String getNonceStr() {
        String strTime = new Date().toString();
        strTime = strTime.substring(8, strTime.length());
        String strRandom = buildRandom(4) + "";
        return strTime + strRandom;
    }

    public static String getMchBillNo() {
        StringBuilder sb = new StringBuilder();
        String strTime = String.valueOf(System.currentTimeMillis());
        return sb.append(strTime).append(buildRandom(4)).toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    public static Map<String, String> getPrepareId(UnifiedorderDto unifiedorderDto) throws Exception {
        Map params = CommonUtil.objectToMap(unifiedorderDto);
        String dataXml = WXPayUtil.generateSignedXml(params, Conf.get("wx.api.key"));
        String resXml = HttpUtil.postData(WXPayConstants.UNIFIEDORDER_URL, dataXml);
        Map<String, String> result = WXPayUtil.xmlToMap(resXml);
        return result;
    }

    public static Map<String, String> getRequestXml() throws Exception {
        return WXPayUtil.xmlToMap(getRequestParams());
    }

    public static String getRequestParams() throws Exception {
        StringBuffer sb = new StringBuffer();
        String s = "";
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(Context.getHttpServletRequest().getInputStream(), "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        log.info(sb.toString());
        return sb.toString();
    }

    //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
    public static void resNotifySuccess() {
        String resXml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        try {
            BufferedOutputStream outBuffer = new BufferedOutputStream(Context.getHttpServletResponse().getOutputStream());
            outBuffer.write(resXml.getBytes());
            outBuffer.flush();
            outBuffer.close();
        } catch (Exception e) {
            log.error("支付回调成功返回失败");
        }
    }

    public static ModelAndView auth2(String state, String promoteType) {
        String authUrl = TemplateUtils.replaceAll(Conf.get("wx.oauth2.url"), Conf.get("wx.appId"),
                URLEncoder.encode(TemplateUtils.replaceAll(Conf.get("promote.wx.auth.callback.url"), promoteType)),
                Conf.get("wx.request.auth.type." + promoteType + ":snsapi_userinfo"), state);
        return new ModelAndView("redirect:" + authUrl);
    }

    public static GetTokenByCodeResultDto getAccessTokenByCode(String code) {
        String url = TemplateUtils.replaceAll(Conf.get("wx.access.token.code.url"), Conf.get("wx.appId"), Conf.get("wx.secret"), code);
        return get(url, GetTokenByCodeResultDto.class);
    }

    public static WxUserModel getServerUserInfo(String urlKey, String openId, String token) {
        String url = TemplateUtils.replaceAll(Conf.get(urlKey), token, openId);
        WxUserDto wxUserDto = post(url, new HashMap(), WxUserDto.class);
        WxUserModel wxUserModel = new WxUserModel();
        BeanUtils.copyProperties(wxUserDto, wxUserModel);
        wxUserModel.setOpenId(wxUserDto.getOpenid());
        try {
            if (wxUserDto.getNickname() != null) {
                // 将微信昵称转为 UTF-8
                wxUserModel.setNickname(new ByteArrayInputStream(wxUserDto.getNickname().getBytes("UTF-8")));
            }
        } catch (UnsupportedEncodingException e) {
        }
        return wxUserModel;

    }

    public static synchronized JsApiTicketDto getJsapiTicket() {
        if (jsApiTicketDto == null || jsApiTicketDto.getExpire().before(new Date())) {
            String url = TemplateUtils.replaceAll(Conf.get("wx.jsapi.ticket"), getAccessToken().getAccess_token());
            jsApiTicketDto = get(url, JsApiTicketDto.class);
        }
        return jsApiTicketDto;
    }

    public static JsApiDto genJsApiDto(String url) throws Exception {
        WxMpService wxMpService = Context.getBean(WxMpService.class);
        JsApiDto jsApiDto = new JsApiDto(wxMpService.getWxMpConfigStorage().getAppId());
        if (Conf.enable("wx.jsApi.enable")) {
            WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);
            jsApiDto.setNoncestr(jsapiSignature.getNonceStr());
            jsApiDto.setSignature(jsapiSignature.getSignature());
            jsApiDto.setTimestamp(String.valueOf(jsapiSignature.getTimestamp()));
        }
        return jsApiDto;
    }

    public static JsApiDto genJsApiDto() throws Exception {
        return genJsApiDto(ParamsUtils.getLocalUrl());
    }

    public static synchronized RedPackReturnDto redpack(RedpackDto redpackDto) throws Exception {
        IWxRedPackResultService resultService = Context.getBean(IWxRedPackResultService.class);
        WxRedPackResultModel resultModel = new WxRedPackResultModel();
        resultModel.setOpenId(redpackDto.getRe_openid());
        resultModel.setPromoteType(Context.getPromoteType());
        resultModel = resultService.selectOne(resultModel);

        if (resultModel != null && "SUCCESS".equals(resultModel.getResultCode())) {
            throw new StatelessException(ErrorMessage.getFailure("redPack again", "您已领取红包"));
        }

        if (resultModel == null) {
            resultModel = new WxRedPackResultModel();
            redpackDto.setNonce_str(WxUtils.getMchBillNo());
            redpackDto.setMch_billno(WxUtils.getMchBillNo());
            resultModel.setOpenId(redpackDto.getRe_openid());
            resultModel.setPromoteType(Context.getPromoteType());
            resultModel.setMchBillno(redpackDto.getMch_billno());
            resultService.insertSelective(resultModel);
        } else {
            //发送失败的用原来的商户订单重新发送
            redpackDto.setNonce_str(WxUtils.getMchBillNo());
            redpackDto.setMch_billno(resultModel.getMchBillno());
        }

        RedPackReturnDto redPackReturnDto = WxCertUtils.postWithCert(Conf.get("wx.redpack.url"), Conf.get("wx.redpack.cert"), redpackDto, RedPackReturnDto.class);
        resultModel.setResultCode(redPackReturnDto.getResult_code());
        resultModel.setErrCode(redPackReturnDto.getErr_code());
        resultModel.setTotalAmount(redPackReturnDto.getTotal_amount());

        if ("SUCCESS".equals(resultModel.getResultCode())) {
            resultModel.setSuccessTime(new Date());
        }

        resultService.updateByPrimaryKey(resultModel);
        return redPackReturnDto;
    }

    public static boolean isSubscribe(String openId) {
        WxUserModel wxUserModel = WxUtils.getServerUserInfo("wx.user.info.subscript", openId, getAccessToken().getAccess_token());
        return wxUserModel != null && "1".equals(wxUserModel.getSubscribe());
    }

    public static ResponseEntity<byte[]> downloadMedia(String mediaId) {
        String token = getAccessToken().getAccess_token();
        String url = Conf.get("wx.media.get.url");
        url = TemplateUtils.replaceAll(url, token, mediaId);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> response = RESTUtils.rest().exchange(url,
                HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
        return response;
    }
}
