package com.blackflagbin.common.http;

import com.blackflagbin.common.base.IApiException;

public class ApiException extends RuntimeException implements IApiException{

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

