package com.ningyuan.route.controller;

import com.ningyuan.route.dto.ShopUserDto;
import com.ningyuan.route.dto.ShopUserQueryDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user/")
public class ShopUserController {


    @PostMapping("list")
    @ResponseBody
    public void listUser() {
        System.out.println("分页查询用户");
    }

    @PostMapping("update")
    @ResponseBody
    public void updateUser(ShopUserDto shopUserDto) {
        System.out.println("更新用户");
    }


    @PostMapping("query")
    @ResponseBody
    public void queryUser(ShopUserQueryDto queryDto) {
        System.out.println("查询用户");
    }
}
