package com.blackflagbin.common.http;

public class ApiException extends RuntimeException {

    private int    mResultCode;
    private String mMessage;

    public ApiException(int resultCode, String message) {
        mResultCode = resultCode;
        mMessage = message;
    }

    public int getResultCode() {
        return mResultCode;
    }

    @Override
    public String getMessage() {
        return mMessage;
    }

}

