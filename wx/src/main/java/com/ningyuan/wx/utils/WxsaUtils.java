package com.ningyuan.wx.utils;

import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.wx.constant.WxsaConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: zongrui_cai
 * @Date: 2019/8/22 14:42
 */
public class WxsaUtils {
    protected static Logger log = LoggerFactory.getLogger(WxsaUtils.class);

    private WxsaUtils() {
    }

    /**
     * 获取wx_relate 上params中 某个参数的值
     *
     * @param commonParams
     * @param key          params中的参数名
     * @return
     */
    public static String getFromCommonParams(String commonParams, String key) {
        if (StringUtils.isEmpty(commonParams) || StringUtils.isEmpty(key)) {
            return null;
        }

        String val = null;
        String[] arr = StringUtils.split(commonParams, WxsaConstant.commonRelate.SIGN_BETWEEN_PARAM);
        for (String s : arr) {
            String value = StringUtils.substringAfter(s, key + WxsaConstant.commonRelate.SIGN_BETWEEN_KV);
            if (StringUtils.isNotBlank(value)) {
                val = value;
                break;
            }
        }
        return val;
    }

    public static void checkToken(String token) throws Exception {
        if (!org.apache.commons.lang3.StringUtils.equals(token, Conf.get("promote.notify.common.token"))) {
            throw new StatelessException(ErrorMessage.getFailure("invalid token", "无效token"));
        }
    }
}
