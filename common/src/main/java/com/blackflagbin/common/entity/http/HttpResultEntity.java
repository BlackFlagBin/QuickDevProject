package com.blackflagbin.common.entity.http;


import java.io.Serializable;

public class HttpResultEntity<T> implements Serializable {

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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }


}
