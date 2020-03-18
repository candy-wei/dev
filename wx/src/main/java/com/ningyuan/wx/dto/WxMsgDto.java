package com.ningyuan.wx.dto;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

/***
 * Author: YeJian
 * Create on: 2018/7/2
 * Email: amazeconch@gmail.com
 * Explanation: 小程序消息模板
 */
public class WxMsgDto {

    @NotEmpty
    private String touser;
    @NotEmpty
    private String template_id;
    @NotEmpty
    private String page;
    @NotEmpty
    private String form_id;
    private String emphasis_keyword;

    @NotEmpty
    private Map<String, Map<String,String>> data = new HashMap<>();

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

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public String getEmphasis_keyword() {
        return emphasis_keyword;
    }

    public void setEmphasis_keyword(String emphasis_keyword) {
        this.emphasis_keyword = emphasis_keyword;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    /**
     * 注意要按模板变量的先后顺序
     * @param params
     */
    public  void string2Data(String ...params) {
        for(int i=0;i<params.length;i++){
            Map<String,String> map =new HashMap<>();
            map.put("value",params[i]);
            this.data.put("keyword" + (i+1), map);
        }
    }



}
