package com.tareas.core.exceptions;

import com.tareas.core.datatransfer.ErrorCause;
import com.tareas.core.datatransfer.ErrorData;
import com.tareas.core.errors.ErrorCode;
import org.springframework.http.HttpStatus;

public class ApiException extends Exception{
    private ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

    public ErrorData getResult() {
        ErrorData result = new ErrorData();
        result.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        result.setMessage(getMessage());
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.getErrorCauses().add(new ErrorCause(getErrorCode().getCode(), getErrorCode().getDescription()));

        return result;
    }

    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
