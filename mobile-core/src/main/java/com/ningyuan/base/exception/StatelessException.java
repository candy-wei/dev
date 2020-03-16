package com.ningyuan.base.exception;

public class StatelessException extends BaseException {

    public StatelessException() {
        super.errorMessage = ErrorMessage.getFailure();
    }

    public StatelessException(ErrorMessage errorMessage) {
        super(errorMessage.getErrorCode(), errorMessage);
    }

    public StatelessException(String message, ErrorMessage errorMessage) {
        super(message, errorMessage);
    }

    public StatelessException(String message, Throwable cause, ErrorMessage errorMessage) {
        super(message, cause, errorMessage);
    }

    public StatelessException(Throwable cause, ErrorMessage errorMessage) {
        super(cause, errorMessage);
    }

    public StatelessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorMessage errorMessage) {
        super(message, cause, enableSuppression, writableStackTrace, errorMessage);
    }
}
