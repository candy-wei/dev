package com.ningyuan.system.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.base.exception.StatelessException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;

/**
 * @Author: zongrui_cai
 * @Date: 2019/10/11 9:48
 */
@Controller
@RequestMapping("rest/sys/invoke")
public class SysInvokeController extends BaseController {

    @ApiOperation(value = "wxMpService调用接口")
    @GetMapping("wxMpService")
    @ResponseBody
    public Object benn(String token, String innerService, String methodName, String... params) throws Exception {
        if (!StringUtils.equals(token, Conf.get("promote.notify.common.token"))) {
            throw new StatelessException(ErrorMessage.getFailure("invalid token", "无效token"));
        }
        if (StringUtils.isEmpty(innerService) || StringUtils.isEmpty(methodName)) {
            throw new StatelessException(ErrorMessage.getFailure("invalid url params", "invalid url params"));
        }
        if (Conf.enable("sys.invoke.close")) {
            throw new StatelessException(ErrorMessage.getFailure("close", "close"));
        }
        WxMpService wxMpService = Context.getBean(WxMpService.class);
        for (Method getService : wxMpService.getClass().getMethods()) {
            if (StringUtils.equals(getService.getName(), innerService)) {
                Object res = invokeMethod(getService.invoke(wxMpService), methodName, params);
//                log.info("res:{}", JSON.toJSONString(res));
                return res;
            }
        }
        return "not_innerService";
    }

    private Object invokeMethod(Object obj, String methodName, String... params) throws Exception {
        for (Method method : obj.getClass().getMethods()) {
            if (StringUtils.equals(method.getName(), methodName)) {
                if (params == null) {
                    return method.invoke(obj);
                }
                Object[] args = new Object[params.length];
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != params.length) {   //匹配 参数个数
                    continue;
                }
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class type = parameterTypes[i];
                    if (params[i] instanceof String && type.getName().equals("java.util.Date")) {
//                        args[i] = DateUtils.string2Date(params[i]);
                        continue;
                    }
//                    args[i] = JSON.parseObject(params[i], type);
                }
                return method.invoke(obj, args);
            }
        }
        return "not_method";
    }

}
