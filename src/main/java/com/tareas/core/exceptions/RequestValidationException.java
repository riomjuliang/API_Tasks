package com.tareas.core.exceptions;

import com.tareas.core.errors.ErrorCode;

public class RequestValidationException extends ApiException {
    public RequestValidationException() {
        super();
        setErrorCode(ErrorCode.REQUEST_VALIDATION_FAILED);
    }

    public RequestValidationException(String message) {
        super(message);
        setErrorCode(ErrorCode.REQUEST_VALIDATION_FAILED);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
        setErrorCode(ErrorCode.REQUEST_VALIDATION_FAILED);
    }

    public RequestValidationException(Throwable cause) {
        super(cause);
        setErrorCode(ErrorCode.REQUEST_VALIDATION_FAILED);
    }
}
