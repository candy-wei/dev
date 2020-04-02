package com.ningyuan.wx.utils;

import com.ningyuan.wx.service.IWxRelateService;
import com.ningyuan.wx.service.IWxUserService;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import com.ningyuan.utils.MaskUtils;
import com.ningyuan.utils.TemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class WxShareUtils {

    public static void genShareUrl(IWxRelateService relateService) throws Exception {

        if (!Boolean.parseBoolean(Conf.get("wx.share.enable"))) {
            return;
        }

        HttpServletRequest request = Context.getHttpServletRequest();
        IWxUserService wxUserService = Context.getBean(IWxUserService.class);
        String openId = Context.containTreadLocal("openId") ? Context.getOpenId() : ServletRequestUtils.getStringParameter(request, "openId");
        String promoteType = Context.containTreadLocal("promoteType") ? Context.getPromoteType() : ServletRequestUtils.getStringParameter(request, "promoteType");
        if (StringUtils.isEmpty(promoteType)) {
            promoteType = Conf.get("wx.share.promote.type");
        }

        if (!StringUtils.isEmpty(openId)) {
            String maskNickname = MaskUtils.build("wx.share.nickname.mask").mask(wxUserService.getDtoByOpenId(openId).getNickname());
            request.setAttribute("shareTitle", TemplateUtils.replaceAll(Conf.get(promoteType + ".wx.share.title"), maskNickname));
            request.setAttribute("shareDesc", TemplateUtils.replaceAll(Conf.get(promoteType + ".wx.share.desc"), maskNickname));
            if (Conf.containsKey(promoteType + ".wx.share.thumb.url")) {
                request.setAttribute("thumbUrl", Conf.get(promoteType + ".wx.share.thumb.url"));
            } else {
                request.setAttribute("thumbUrl", Conf.get("wx.share.thumb.url"));
            }
            request.setAttribute("shareLink", TemplateUtils.replaceAll(getShareUrl(openId, relateService), openId));
        }
    }

    public static String getShareUrl(String openId, IWxRelateService relateService) {
        Map<String, Object> params = relateService.genShareParams(openId);
        HttpServletRequest request = Context.getHttpServletRequest();
        String shareUrl = request.getScheme() + "://" + request.getServerName() + Conf.get("wx.share.uri") + "&";
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (shareUrl.contains(entry.getKey().concat("="))) {
                    continue;
                }
                shareUrl += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        return shareUrl.substring(0, shareUrl.length() - 1);
    }

}
