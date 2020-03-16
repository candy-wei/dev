package com.ningyuan.system.controller;

import com.ningyuan.base.BaseController;
import com.ningyuan.base.datasource.CacheEnableAspect;
import com.ningyuan.base.exception.BaseException;
import com.ningyuan.base.exception.ErrorMessage;
import com.ningyuan.core.Conf;
import com.ningyuan.core.CustomizedPropertyConfigurer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value = "非重启加载Controller", tags = {"非重启加载调用接口"})
@RequestMapping("rest")
public class ReloadController extends BaseController {

    /**
     * <p>Conf修改后重新加载
     * @author : ZengRongChang
     * @version : v0.0.1
     */
    @ApiOperation(value = "sysparams修改后重新加载")
    @RequestMapping(value = "reload/conf", method = RequestMethod.GET)
    @ResponseBody
    public ErrorMessage reloadConf() throws BaseException {
        ErrorMessage errorMessage = ErrorMessage.getSuccess();
        try{
            CustomizedPropertyConfigurer.loadPropsFromDb();
        }catch (Exception e){
           errorMessage.setErrorCode("reloadConfFailure");
           errorMessage.setErrorMsg("FAILUER");
           throw new BaseException(e, errorMessage);
        }
        return errorMessage;
    }

    @RequestMapping(value = "clear/cache", method = RequestMethod.GET)
    @ApiOperation(value = "清除缓存")
    @ResponseBody
    public ErrorMessage clearCache() throws BaseException {
        ErrorMessage errorMessage = ErrorMessage.getSuccess();
        try{
            CacheEnableAspect.clear();
        }catch (Exception e){
            errorMessage.setErrorCode("reloadCacheFailure");
            errorMessage.setErrorMsg("FAILUER");
            throw new BaseException(e, errorMessage);
        }
        return errorMessage;
    }

    @RequestMapping(value = "query/conf", method = RequestMethod.GET)
    @ApiOperation(value = "查询缓存列表")
    @ResponseBody
    public String queryConf(String key) {
        return Conf.getProperties().getProperty(key);
    }
}