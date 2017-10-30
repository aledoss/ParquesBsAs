package com.example.ndiaz.parquesbsas.gsonresult;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lenwe on 23/10/2017.
 */

public class NetworkResponse<T> {
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("response")
    @Expose
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
