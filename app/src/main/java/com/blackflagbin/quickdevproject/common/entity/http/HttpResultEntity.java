package com.blackflagbin.quickdevproject.common.entity.http;


import com.blackflagbin.common.http.transformer.IHttpResultEntity;

import java.io.Serializable;

public class HttpResultEntity<T> implements Serializable, IHttpResultEntity<T> {

    public int code = 0;

    public String message = "";

    public boolean error;
    public T       results;

    public HttpResultEntity() {
    }

    public HttpResultEntity(int code, String message, boolean success, T data) {
        this.code = code;
        this.message = message;
        this.error = success;
        this.results = data;
    }


    @Override
    public boolean isSuccess() {
        return !error;
    }

    public int getErrorCode() {
        return code;
    }


    public String getErrorMessage() {
        return message;
    }

    @Override
    public T getResult() {
        return results;
    }

}
