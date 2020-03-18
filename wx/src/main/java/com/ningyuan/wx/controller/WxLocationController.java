package com.ningyuan.wx.controller;

import com.ningyuan.wx.model.WxLocationModel;
import com.ningyuan.wx.service.IWxLocationService;
import com.ningyuan.wx.utils.WxLocationUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: ZengRongChang
 * @Description:
 * @Date: Created in 14:27 on 2018/10/17.
 * @Modified by:
 */
@Api(value = "WxController", tags = {"微信不拦截调用接口"})
@Controller
@RequestMapping("/wx")
public class WxLocationController {

    @Autowired
    private IWxLocationService wxLocationService;

    @ApiOperation(value = "获得地理位置")
    @RequestMapping(value = "location/{openId}", method = RequestMethod.POST)
    public void location(@ApiParam(name = "openId", value = "openId", required = true) @PathVariable("openId") String openId,
                         @ApiParam(name = "lat", value = "维度", required = true) String lat,
                         @ApiParam(name = "lng", value = "经度", required = true) String lng) throws Exception {
        WxLocationModel wxLocationModel = WxLocationUtils.transform(lat, lng);
        wxLocationModel.setOpenId(openId);

        WxLocationModel wxLocationModelOld = new WxLocationModel();
        wxLocationModelOld.setOpenId(openId);
        wxLocationModelOld = wxLocationService.selectOne(wxLocationModelOld);
        if (wxLocationModelOld != null) {
            wxLocationModel.setId(wxLocationModelOld.getId());
            wxLocationService.updateByPrimaryKey(wxLocationModel);
        } else {
            wxLocationService.insertSelective(wxLocationModel);
        }
    }

}
