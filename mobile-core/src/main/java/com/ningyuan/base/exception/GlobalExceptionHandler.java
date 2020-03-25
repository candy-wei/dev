package com.ningyuan.base.exception;

import com.mchange.rmi.NotAuthorizedException;
import com.ningyuan.core.Conf;
import com.ningyuan.core.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalExceptionHandler {
    protected transient Logger logger = LoggerFactory.getLogger(getClass());

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
//    @AspectBefore(handler = "exceptionTest")
    public @ResponseBody
    ErrorMessage handleNotAuthorizedException(NotAuthorizedException e) {
        logger.error("权限访问错误", e);
        return new ErrorMessage(e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException.class)
//    @AspectBefore(handler = "exceptionTest")
    public ModelAndView handleDataAccessException(DataAccessException e) {
        logger.error("数据访问错误:", e);
        return new ModelAndView("common/net_err");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
//    @AspectBefore(handler = "exceptionTest")
    public ModelAndView handleException(Exception e) {
        String openId="";
        if(Context.containTreadLocal("openId")){
            openId =Context.getTreadLocal("openId");
        }
        logger.error("系统内部错误, openId:{}:",openId, e);
        return new ModelAndView("common/net_err");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(StatelessException.class)
//    @AspectBefore(handler = "exceptionTest")
    public @ResponseBody
    ErrorMessage handleStatelessException(BaseException e) {
        logger.error("业务处理错误:", e);
        return e.getErrorMessage();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ViewException.class)
//    @AspectBefore(handler = "exceptionTest")
    public ModelAndView handleViewlessException(ViewException e) {
        logger.error("业务处理错误:");

        if(e.getModelAndView() != null){
            return e.getModelAndView();
        }

        ModelAndView modelAndView = new ModelAndView(Conf.get(e.getViewName()));
        modelAndView.addObject("errorMessage", e.getErrorMessage());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BaseException.class)
//    @AspectBefore(handler = "exceptionTest")
    public @ResponseBody
    ErrorMessage handleBaseException(BaseException e) {
        logger.error("业务处理错误:", e);
        return e.getErrorMessage();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
//    @AspectBefore(handler = "exceptionTest")
    public @ResponseBody
    ErrorMessage handleBindException(BindException e) {
        logger.error("请求参数错误:", e);
        return new ErrorMessage("invalidParam","请求参数错误:", e.getFieldErrors());
    }

}