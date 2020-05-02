package com.ningyuan.route.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.core.Context;
import com.ningyuan.route.dto.SettingDto;
import com.ningyuan.route.dto.ShopUserDto;
import com.ningyuan.route.dto.ShopUserQueryDto;
import com.ningyuan.route.service.IShopUserService;
import com.ningyuan.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user/")
public class ShopUserController {

    @Autowired
    private IShopUserService shopUserService;

    @PostMapping("list/confirm")
    @ResponseBody
    public PageInfo<ShopUserDto> listConfirmUser(ShopUserQueryDto queryDto) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(shopUserService.listConfirmUser(queryDto));
    }

    @PostMapping("list/unConfirm")
    @ResponseBody
    public PageInfo<ShopUserDto> listUnConfirmUser(ShopUserQueryDto queryDto) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(shopUserService.listUnConfirmUser(queryDto));
    }

    @PostMapping("update")
    @ResponseBody
    public ShopUserDto updateUser(ShopUserDto shopUserDto) {
        if (shopUserDto.getId() != null) {
            shopUserService.updateUserByid(shopUserDto);
        }
        return shopUserDto;
    }

    @PostMapping("confirm/update")
    @ResponseBody
    public ErrorMessage updateConfirm(Long id) {
        if (id != null) {
            shopUserService.updateConfirm(id);
            return ErrorMessage.getSuccess();
        }
        return ErrorMessage.getFailure();
    }

    @GetMapping("getSetting")
    @ResponseBody
    public SettingDto getSetting() {
        return shopUserService.getSettingDto();
    }

    @PostMapping("updateSetting")
    @ResponseBody
    public SettingDto updateSetting(SettingDto settingDto) {
        if (settingDto != null) {
            shopUserService.updateSetting(settingDto);
        }
        return settingDto;
    }

    @PostMapping("updateRedpacketSum")
    @ResponseBody
    public ErrorMessage updateRedpacketSum(Long id, String openId, Integer addAmount, Integer minusAmount) {
        Integer redpacketAmount = shopUserService.getShopCustomer(id);
        if (redpacketAmount != null && addAmount != null && StringUtil.isNotEmpty(openId)) {
            shopUserService.addRedpacketAmount(id, openId, addAmount, redpacketAmount + addAmount);
        }

        if (redpacketAmount != null && minusAmount != null && redpacketAmount - minusAmount > 0) {
            shopUserService.minusRedpacketAmount(id, redpacketAmount - minusAmount);
        }
        return ErrorMessage.getSuccess();
    }

}
