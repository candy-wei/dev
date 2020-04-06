package com.ningyuan.route.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.core.Context;
import com.ningyuan.route.dto.ShopUserDto;
import com.ningyuan.route.dto.ShopUserQueryDto;
import com.ningyuan.route.service.IShopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user/")
public class ShopUserController {

    @Autowired
    private IShopUserService shopUserService;

    @PostMapping("list")
    @ResponseBody
    public PageInfo<ShopUserDto> listUser(ShopUserQueryDto queryDto) {
        PageHelper.startPage(Context.getHttpServletRequest());
        return PageInfo.of(shopUserService.listUser(queryDto));
    }

    @PostMapping("update")
    @ResponseBody
    public ShopUserDto updateUser(ShopUserDto shopUserDto) {
        if (shopUserDto.getId() != null) {
            shopUserService.updateUserByid(shopUserDto);
        }
        return shopUserDto;
    }

}
