package com.ningyuan.wx.service;

import com.ningyuan.wx.model.WxRelateModel;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/21 14:11
 */
public interface IWxCommonRelateView {

    ModelAndView viewByType(WxRelateModel wxRelateModel);
}
