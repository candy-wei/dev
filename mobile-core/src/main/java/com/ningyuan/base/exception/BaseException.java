package com.ningyuan.base.exception;

public class BaseException extends Exception {

    protected ErrorMessage errorMessage;

    public BaseException() {
        this.errorMessage = ErrorMessage.getFailure();
    }

    public BaseException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BaseException(String message, ErrorMessage errorMessage) {
        super(message);
        this.errorMessage = errorMessage;
    }

    public BaseException(String message, Throwable cause, ErrorMessage errorMessage) {
        super(message, cause);
        this.errorMessage = errorMessage;
    }

    public BaseException(Throwable cause, ErrorMessage errorMessage) {
        super(cause);
        this.errorMessage = errorMessage;
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorMessage errorMessage) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
