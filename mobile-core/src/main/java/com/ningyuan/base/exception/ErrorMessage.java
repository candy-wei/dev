package com.ningyuan.base.exception;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessage {

    private String errorCode;

    private String errorMsg;

    private List<FieldError> fieldErrors = new ArrayList<FieldError>();

    public ErrorMessage() {
    }

    public ErrorMessage(String errorMsg){
        this.errorMsg = errorMsg;
    }

    public ErrorMessage(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public  ErrorMessage(String errorCode, String errorMsg, List<FieldError> fieldErrors){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.fieldErrors = fieldErrors;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public static ErrorMessage getSuccess(){
        return  new ErrorMessage("SUCCESS", "请求成功");
    }

    public static ErrorMessage getSuccess(String errorCode, String errorMsg){
        return  new ErrorMessage(errorCode, errorMsg);
    }

    public static ErrorMessage getFailure(){
        return  new ErrorMessage("FAILURE", "请求失败");
    }

    public static ErrorMessage getFailure(String errorCode){
        return  new ErrorMessage(errorCode, "请求失败");
    }

    public static ErrorMessage getFailure(String errorCode, String errorMsg){
        return  new ErrorMessage(errorCode, errorMsg);
    }

    public static ErrorMessage getErrorMessage(boolean result) {
        return result ? ErrorMessage.getSuccess() : ErrorMessage.getFailure();
    }
}
