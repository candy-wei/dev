package com.ningyuan.route.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.core.Context;
import com.ningyuan.route.dto.ShopAddressDto;
import com.ningyuan.route.dto.ShopInviterDto;
import com.ningyuan.route.dto.ShopOrderDto;
import com.ningyuan.route.service.IShopCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("customer")
public class ShopCustomerController {

    @Autowired
    private IShopCustomerService customerService;

    @PostMapping("invite/list")
    @ResponseBody
    public PageInfo<ShopInviterDto> getInviteList(String mobile) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(customerService.getInviteList(mobile));
    }

    @PostMapping("address/list")
    @ResponseBody
    public PageInfo<ShopAddressDto> getAddressList(String mobile, String defaultAddress) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(customerService.getAddressList(mobile, defaultAddress));
    }

    @PostMapping("order/list")
    @ResponseBody
    public PageInfo<ShopOrderDto> getOrderList(String orderSn) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(customerService.getOrderList(orderSn));
    }
}
