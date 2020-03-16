package com.ningyuan.pos.controller;

import com.ningyuan.base.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("test")
public class AddressController extends BaseController {

    @ApiOperation(value = "测试Controller321")
    @GetMapping(value = "index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("123");
        return mv;
    }

    @ApiOperation(value = "测试Controller")
    @RequestMapping(value = "index1")
    public String index1() {
        return "index11111";
    }

    @ApiOperation(value = "测试Controller")
    @RequestMapping(value = "index2")
    @ResponseBody
    public String index2() {
        return "index2222";
    }
}
