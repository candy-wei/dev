package com.ningyuan.wx.dto;

import com.alibaba.fastjson.JSONObject;
import com.ningyuan.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;

/***
 * Author: YeJian
 * Create on: 2019/10/14
 * Email: amazeconch@gmail.com
 * Explanation: 小程序订阅消息模板类
 */
public class WxSubscribeMessageDto {
    private String touser;
    private String template_id;
    private String page;

    private Map<String, Object> data = new HashMap<>();


    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    /**
     * 调用该方法把数据格式化成目标类型数据
     *
     * @param jsonText json字符串
     * @param map      需要替换的数据集
     */
    public void jsonToMap(String jsonText, Map<String, String> map) {
        JSONObject jsonObject = JSONObject.parseObject(StringUtil.replaceAll(jsonText, map));
        String id = jsonObject.getString("id");
        if (tk.mybatis.mapper.util.StringUtil.isNotEmpty(id)) {
            this.template_id = id;
        }
        jsonObject.getJSONObject("data").forEach((key, value) -> this.data.put(key, value));
    }


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
