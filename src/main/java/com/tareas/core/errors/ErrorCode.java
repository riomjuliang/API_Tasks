package com.tareas.core.errors;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    REQUEST_VALIDATION_FAILED(1001, "Request validation failed", HttpStatus.BAD_REQUEST),
    BAD_REQUEST(1001, "Request validation failed", HttpStatus.BAD_REQUEST),
    INVALID_USER(1018, "Invalid user", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(1999, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(1030, "Resource Not Found", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(1019, "User without authorization", HttpStatus.UNAUTHORIZED);

    private int code;
    private String description;
    private HttpStatus httpStatus;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    ErrorCode(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public static ErrorCode get(int code) {
        for (ErrorCode errorCode: ErrorCode.values()) {
            if (errorCode.code == code) {
                return errorCode;
            }
        }
        return INTERNAL_SERVER_ERROR;
    }
}
