package com.ningyuan.wx.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.wx.handler.WxsaOutMsgHandler;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(value = "rest/wxsa", method = {RequestMethod.GET, RequestMethod.POST})
@Controller
public class WxsaRestController extends BaseController {

    @Autowired
    protected WxMpService wxMpService;
    @Autowired
    private WxsaOutMsgHandler outMsgHandler;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/downloadAndroidApp")
    public ModelAndView downloadAndroidApp() {
        return new ModelAndView("redirect:" + Conf.get("wxsa.app.android.download.url"));
    }

    @ResponseBody
    @RequestMapping(value = "/genQr")
    public Object genQr(String sceneId, String token) throws WxErrorException, StatelessException {
        if (StringUtils.isEmpty(sceneId)) {
            throw new StatelessException(ErrorMessage.getFailure("invalid sceneId", "无效sceneId"));
        }
        if (!StringUtils.equals(token, Conf.get("promote.ht.handler.token"))) {
            throw new StatelessException(ErrorMessage.getFailure("invalid token", "无效token"));
        }
        // 临时ticket ,最多30天, 即2592000秒
        WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(sceneId, 2592000);
        log.info("ticket:{}", ticket);
        return ticket;
    }

    /**
     * 微信公众号webservice主服务接口，提供与微信服务器的信息交互
     */
    @RequestMapping(value = "/core")
    public void wechatCore(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

        if (!this.wxMpService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        String echoStr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echoStr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            String echoStrOut = String.copyValueOf(echoStr.toCharArray());
            response.getWriter().println(echoStrOut);
            return;
        }

        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type"))
                ? "raw"
                : request.getParameter("encrypt_type");

        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = outMsgHandler.handle(inMessage);
            if (outMessage == null) {
                response.getWriter().write("");
            } else {
                response.getWriter().write(outMessage.toXml());
            }
            return;
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                    request.getInputStream(), wxMpService.getWxMpConfigStorage(), timestamp, nonce,
                    msgSignature);
            this.log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = outMsgHandler.handle(inMessage);
            if (outMessage == null) {
                response.getWriter().write("");
            } else {
                response.getWriter().write(outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage()));
            }
            return;
        }

        response.getWriter().println("不可识别的加密类型");
    }

}
