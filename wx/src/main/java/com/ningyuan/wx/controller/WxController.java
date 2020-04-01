package com.ningyuan.wx.controller;

import com.ningyuan.wx.dto.GetBrandWCPayRequestDto;
import com.ningyuan.wx.service.IWxRelateService;
import com.ningyuan.wx.service.impl.WxCommonRelateServiceImpl;
import com.ningyuan.wx.utils.WxUtils;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.ParamsUtils;
import com.ningyuan.utils.TemplateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 14:27 on 2018/10/17.
 * @Modified by:
 */
@Api(value = "WxController", tags = {"微信不拦截调用接口"})
@Controller
@RequestMapping("wx")
public class WxController {

    @Autowired
    private WxCommonRelateServiceImpl wxCommonRelateService;

    @ApiOperation(value = "触发微信支付")
    @RequestMapping(value = "trigger/{id}/{openId}", method = RequestMethod.GET)
    public String trigger(@ApiParam(name = "promoteType", value = "promoteType", required = true) @PathVariable("promoteType") String promoteType,
                          @ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId) throws Exception {
        IWxRelateService wxRelateService = Context.getBean(promoteType, IWxRelateService.class);
        wxRelateService.verify(openId);
        return "redirect:" + TemplateUtils.replaceAll(Conf.get("wx.common.pay.url"), promoteType, openId);
    }

    @ApiOperation(value = "通用微信支付")
    @RequestMapping(value = "pay/{id}/{openId}", method = RequestMethod.GET)
    public ModelAndView pay(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId,
                            @ApiParam(name = "id", value = "预约或预存id", required = true) @PathVariable("id") String id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("wxpay/trigger");
        wxCommonRelateService.preHandle(openId, id);
        GetBrandWCPayRequestDto payRequestDto = WxUtils.getPayRequestDto(id, openId, wxCommonRelateService.getPayPrice(openId));
        modelAndView.addObject("payRequestDto", payRequestDto);
        modelAndView.addObject("redirectUri", TemplateUtils.replaceAll(Conf.get("promote.pay.load.view"), openId));
        return modelAndView;
    }

    @ApiOperation(value = "支付等待")
    @RequestMapping(value = "pay/load/{openId}", method = RequestMethod.GET)
    public ModelAndView pay(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId) {
        ModelAndView modelAndView = new ModelAndView("wxpay/payLoader");
        modelAndView.addObject("redirectUri", ParamsUtils.getRomote() + TemplateUtils.replaceAll(Conf.get("promote.index.view"), openId));
        return modelAndView;
    }

}
