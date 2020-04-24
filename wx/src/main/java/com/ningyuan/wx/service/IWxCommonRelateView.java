package com.ningyuan.wx.service;

import com.ningyuan.wx.model.WxRelateModel;
import org.springframework.web.servlet.ModelAndView;

public interface IWxCommonRelateView {

    ModelAndView viewByType(WxRelateModel wxRelateModel);
}
