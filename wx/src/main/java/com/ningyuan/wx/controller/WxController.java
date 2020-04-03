package com.ningyuan.wx.controller;

import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.ParamsUtils;
import com.ningyuan.utils.TemplateUtils;
import com.ningyuan.wx.dto.GetBrandWCPayRequestDto;
import com.ningyuan.wx.service.IWxRelateService;
import com.ningyuan.wx.utils.WxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "WxController", tags = {"微信不拦截调用接口"})
@Controller
@RequestMapping("wx")
public class WxController {
    Logger logger = LoggerFactory.getLogger(IWxRelateService.class);

    @ApiOperation(value = "触发微信支付")
    @RequestMapping(value = "trigger/{id}/{openId}", method = RequestMethod.GET)
    public String trigger(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId,
                          @ApiParam(name = "id", value = "订单号", required = true) @PathVariable("id") String id) throws Exception {
        IWxRelateService wxRelateService = Context.getBean("orderService", IWxRelateService.class);
        wxRelateService.verify(openId);
        return "redirect:" + TemplateUtils.replaceAll(Conf.get("wx.common.pay.url"), id, openId);
    }

    @ApiOperation(value = "通用微信支付")
    @RequestMapping(value = "pay/{id}/{openId}", method = RequestMethod.GET)
    public ModelAndView pay(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId,
                            @ApiParam(name = "id", value = "订单号", required = true) @PathVariable("id") String orderId) throws Exception {
        ModelAndView modelAndView = new ModelAndView("wxpay/trigger");
        IWxRelateService wxRelateService = Context.getBean("orderService", IWxRelateService.class);
        wxRelateService.preHandle(openId, orderId);
        GetBrandWCPayRequestDto payRequestDto = WxUtils.getPayRequestDto(orderId, openId, wxRelateService.getPayPrice(openId));
        logger.info("payRequestDto:{}", payRequestDto);
        modelAndView.addObject("payRequestDto", payRequestDto);
        modelAndView.addObject("redirectUri", TemplateUtils.replaceAll(Conf.get("pay.load.view"), openId));
        return modelAndView;
    }

    @ApiOperation(value = "支付等待")
    @RequestMapping(value = "pay/load/{openId}", method = RequestMethod.GET)
    public ModelAndView pay(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId) {
        ModelAndView modelAndView = new ModelAndView("wxpay/payLoader");
        modelAndView.addObject("redirectUri", ParamsUtils.getRomote() + TemplateUtils.replaceAll(Conf.get("shop.index.view"), openId));
        return modelAndView;
    }

}
