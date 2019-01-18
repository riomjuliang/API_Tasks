package com.tareas.core.exceptions;

import com.tareas.core.errors.ErrorCode;

public class WithStatusException extends  Exception {
    private int statusCode = 0;
    private ErrorCode errorCode;

    public WithStatusException() {
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public WithStatusException(String message) {
        super(message);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public WithStatusException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public WithStatusException(Throwable cause) {
        super(cause);
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    protected void serErrorValues(int statusCode, ErrorCode errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
