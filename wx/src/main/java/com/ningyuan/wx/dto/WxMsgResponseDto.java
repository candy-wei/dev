package com.ningyuan.wx.dto;

/**
 * Created by YeJian on 2018/7/8.
 * Email: amazeconch@gmail.com
 * Instructions: 模板消息响应
 */
public class WxMsgResponseDto {
    private Long errcode;
    private String errmsg;

    public Long getErrcode() {
        return errcode;
    }

    public void setErrcode(Long errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public static WxMsgResponseDto getSuccess() {
        WxMsgResponseDto responseDto = new WxMsgResponseDto();
        responseDto.setErrcode(0L);
        responseDto.setErrmsg("ok");
        return responseDto;
    }
    public boolean hasSuccess(){
        return Long.valueOf(0L).equals(errcode);
    }
}
