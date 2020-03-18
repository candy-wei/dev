package com.ningyuan.wx.utils;

import com.alibaba.fastjson.JSONObject;
import com.ningyuan.core.Context;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;

/**
 * @Author: hongwei_dai
 * @Date: 2019/11/25 17:45
 */
public class WxSendTemplateMsgUtils {

    public static void sendTemplateMsg(String openId, String templateJson, WxMpService wxMpService) throws WxErrorException {
        JSONObject rootJson = JSONObject.parseObject(templateJson);
        JSONObject dataJson = rootJson.getJSONObject("data");
        String templateId = rootJson.getString("templateId");
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)
                .templateId(templateId)
                .build();
        String url = rootJson.getString("url");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(url)) {
            templateMessage.setUrl(url);
        }
        for (Map.Entry<String, Object> entry : dataJson.entrySet()) {
            templateMessage.addData(new WxMpTemplateData(entry.getKey(), (String) entry.getValue()));
        }
        wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
    }

    public static void sendTemplateMsg(String openId, String templateJson) throws WxErrorException {
        sendTemplateMsg(openId, templateJson, Context.getBean(WxMpService.class));
    }

}
