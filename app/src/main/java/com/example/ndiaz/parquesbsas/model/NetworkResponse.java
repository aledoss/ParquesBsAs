package com.example.ndiaz.parquesbsas.model;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkResponse<T> {

    @JsonProperty("status")
    public Integer status;
    @JsonProperty("message")
    public String message;
    @JsonProperty("response")
    @Nullable
    public T response;

    public NetworkResponse() {
    }

    public NetworkResponse(Integer status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
