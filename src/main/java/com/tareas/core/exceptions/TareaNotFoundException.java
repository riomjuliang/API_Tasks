package com.tareas.core.exceptions;

import com.tareas.core.errors.ErrorCode;

public class TareaNotFoundException extends ApiException {
    public TareaNotFoundException(String message) {
        super(message);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public TareaNotFoundException(String message, Throwable cause) {
        super(message, cause);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public TareaNotFoundException(Throwable cause) {
        super(cause);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public TareaNotFoundException() {
        super();
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
