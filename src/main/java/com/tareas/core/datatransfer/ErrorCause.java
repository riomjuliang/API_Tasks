package com.tareas.core.datatransfer;

public class ErrorCause {
    private int code;
    private String description;

    public ErrorCause(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorCause() {
        super();
    }

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
}
