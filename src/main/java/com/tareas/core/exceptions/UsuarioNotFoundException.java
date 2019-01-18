package com.tareas.core.exceptions;

import com.tareas.core.errors.ErrorCode;

public class UsuarioNotFoundException extends ApiException {
    public UsuarioNotFoundException(String message) {
        super(message);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public UsuarioNotFoundException(String message, Throwable cause) {
        super(message, cause);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public UsuarioNotFoundException(Throwable cause) {
        super(cause);
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public UsuarioNotFoundException() {
        super();
        setErrorCode(ErrorCode.RESOURCE_NOT_FOUND);
    }
}
