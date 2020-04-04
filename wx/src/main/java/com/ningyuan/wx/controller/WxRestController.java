package com.ningyuan.wx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.ningyuan.annotation.AspectAroun;
import com.ningyuan.base.BaseController;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.MaskUtils;
import com.ningyuan.utils.TemplateUtils;
import com.ningyuan.wx.dto.GetTokenByCodeResultDto;
import com.ningyuan.wx.dto.JsApiDto;
import com.ningyuan.wx.dto.WxConfigDto;
import com.ningyuan.wx.service.IWxCommonRelateService;
import com.ningyuan.wx.service.IWxRelateService;
import com.ningyuan.wx.service.IWxUserService;
import com.ningyuan.wx.utils.WxShareUtils;
import com.ningyuan.wx.utils.WxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(value = "WxRestController", tags = {"微信不拦截调用接口"})
@Controller
@RequestMapping("rest/wx")
public class WxRestController extends BaseController {

    @Autowired
    private IWxUserService wxUserService;

    @Autowired
    private IWxCommonRelateService commonRelateService;

    @ApiOperation(value = "用户公众号授权")
    @RequestMapping(value = "auth", method = RequestMethod.GET)
    @AspectAroun(handle = "wxAuthTest")
    public ModelAndView auth(HttpServletRequest request) throws Exception {
        //保存入口关系
        String state = commonRelateService.saveRelate(request);
        //重定向到微信服务端获得openId
        return WxUtils.auth2(state);
    }

    @ApiOperation(value = "授权回调获得openId")
    @RequestMapping(value = "callback", method = RequestMethod.GET)
    public ModelAndView callback(@ApiParam(name = "state", value = "用户关联类别", required = true) @RequestParam String state,
                                 @ApiParam(name = "code", value = "公众号授权code", required = true) @RequestParam String code) throws Exception {
        //从微信获得openId, 并保存微信用户信息
        GetTokenByCodeResultDto getTokenByCodeResultDto = WxUtils.getAccessTokenByCode(code);
        String openId = getTokenByCodeResultDto.getOpenid();
        wxUserService.saveWxUser(getTokenByCodeResultDto);
        //更新关联关系
        commonRelateService.updateRelate(openId, state);
        return commonRelateService.view(openId);
    }

    @ApiOperation(value = "支付成功回调通知")
    @RequestMapping(value = "notify", method = RequestMethod.POST)
    @ResponseBody
    public void payNotify() throws Exception {
        try {
            Map<String, String> reqData = WxUtils.getRequestXml();
            if (!WXPayUtil.isSignatureValid(reqData, Conf.get("wx.api.key"))) {
                return;
            }
            log.info("支付回调通知reqData:{}", JSON.toJSONString(reqData));
            Context.getBean("orderService", IWxRelateService.class).handlePaySuccess(reqData);
            WxUtils.resNotifySuccess();
        } catch (Throwable e) {
            log.error("支付回调通知失败", e);
            throw e;
        }
    }

    @ResponseBody
    @ApiOperation(value = "getShare")
    @RequestMapping(value = "getShare/{openId}", method = RequestMethod.GET)
    public Object getShare(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId) throws Exception {
        IWxUserService wxUserService = Context.getBean(IWxUserService.class);
        Map<String, Object> map = new HashMap<>();
        if (!StringUtils.isEmpty(openId)) {
            String maskNickname = MaskUtils.build("wx.share.nickname.mask").mask(wxUserService.getDtoByOpenId(openId).getNickname());
            map.put("shareTitle", TemplateUtils.replaceAll(Conf.get("wx.share.title"), maskNickname));
            map.put("shareDesc", TemplateUtils.replaceAll(Conf.get("wx.share.desc"), maskNickname));
            if (Conf.containsKey("wx.share.thumb.url")) {
                map.put("thumbUrl", Conf.get("wx.share.thumb.url"));
            } else {
                map.put("thumbUrl", Conf.get("wx.share.thumb.url"));
            }
            map.put("shareLink", WxShareUtils.getShareUrl(openId, commonRelateService));
        }
        return map;
    }

    @ResponseBody
    @ApiOperation(value = "getWxConfig")
    @RequestMapping(value = "getWxConfig", method = RequestMethod.GET)
    public Object getWxConfig(String url) throws Exception {
        WxConfigDto dto = new WxConfigDto();
        JsApiDto jsApiDto = WxUtils.genJsApiDto(url);
        BeanUtils.copyProperties(jsApiDto, dto);
        dto.setNonceStr(jsApiDto.getNoncestr());
        dto.setDebug(Conf.enable("wx.jsApi.config.debug.enable"));
        dto.setJsApiList(JSONObject.parseArray(Conf.get("wx.jsApi.config.menu"), String.class));
        return dto;
    }
}
