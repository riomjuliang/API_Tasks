package com.tareas.core.controller;

import com.tareas.core.datatransfer.ErrorCause;
import com.tareas.core.datatransfer.ErrorData;
import com.tareas.core.errors.ErrorCode;
import com.tareas.core.exceptions.RequestValidationException;
import com.tareas.core.exceptions.TareaNotFoundException;
import com.tareas.core.exceptions.UsuarioNotFoundException;
import com.tareas.core.service.TareaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorsHandler {
    private static final Log logger = LogFactory.getLog(ErrorsHandler.class);

    @ExceptionHandler({NoHandlerFoundException.class, TareaNotFoundException.class, UsuarioNotFoundException.class})
    public HttpEntity<ErrorData> handleNotFound(Exception ex) {

        ErrorData result = new ErrorData();
        result.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        result.setMessage(ex.getMessage());
        result.setStatus(HttpStatus.NOT_FOUND.value());
        result.getErrorCauses().add(new ErrorCause(ErrorCode.RESOURCE_NOT_FOUND.getCode(), ErrorCode.RESOURCE_NOT_FOUND.getDescription()));

        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class, RequestValidationException.class})
    public HttpEntity<ErrorData> handleBadRequest(Exception ex) {

        ErrorData result = new ErrorData();
        result.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        result.setMessage(ex.getMessage());
        result.setStatus(HttpStatus.BAD_REQUEST.value());
        result.getErrorCauses().add(new ErrorCause(ErrorCode.BAD_REQUEST.getCode(), ErrorCode.BAD_REQUEST.getDescription()));

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<ErrorData> handleException(Exception ex) {

        ErrorData result = new ErrorData();
        result.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        result.setMessage(ex.getMessage());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        result.getErrorCauses().add(new ErrorCause(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getDescription()));

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
