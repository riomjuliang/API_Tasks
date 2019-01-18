package com.tareas.core.datatransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ErrorData {
    private String message;
    private String error;
    private int status;
    @JsonProperty("cause")
    @SerializedName("cause")
    private List<ErrorCause> errorCauses;

    public ErrorData(){
        List<ErrorCause> errorCauseList = new ArrayList<>();
        this.setErrorCauses(errorCauseList);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ErrorCause> getErrorCauses() {
        return errorCauses;
    }

    public void setErrorCauses(List<ErrorCause> errorCauses) {
        this.errorCauses = errorCauses;
    }
}
