package com.blackflagbin.common.facade;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.blackflagbin.common.listener.OnTokenExpiredListener;
import com.blankj.utilcode.util.Utils;

import java.util.Map;

/**
 * Created by blackflagbin on 2017/9/11.
 */

public class CommonLibrary {

    private OnTokenExpiredListener mOnTokenExpiredListener;
    private Context                mContext;
    private Class                  mApiClass;
    private String                 mBaseUrl;
    private int                    mTokenExpiredErrorCode;
    private int                    mTokenInvalidErrorCode;
    private Map<String, String>    headerMap;

    private CommonLibrary() {}

    public static CommonLibrary getInstance() {
        return InnerClass.instance;
    }

    public Context getContext() {
        return mContext;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public Class getApiClass() {
        return mApiClass;
    }

    public int getTokenExpiredErrorCode() {
        return mTokenExpiredErrorCode;
    }

    public void setTokenExpiredErrorCode(int tokenExpiredErrorCode) {
        mTokenExpiredErrorCode = tokenExpiredErrorCode;
    }

    public int getTokenInvalidErrorCode() {
        return mTokenInvalidErrorCode;
    }

    public void setTokenInvalidErrorCode(int tokenInvalidErrorCode) {
        mTokenInvalidErrorCode = tokenInvalidErrorCode;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public OnTokenExpiredListener getOnTokenExpiredListener() {
        return mOnTokenExpiredListener;
    }

    public void init(
            @NonNull Application context, @NonNull String baseUrl, @NonNull Class apiClass, @NonNull OnTokenExpiredListener onTokenExpiredListener) {
        mContext = context;
        Utils.init(context);
        mBaseUrl = baseUrl;
        mApiClass = apiClass;
        mOnTokenExpiredListener = onTokenExpiredListener;
    }


    private static class InnerClass {
        static CommonLibrary instance = new CommonLibrary();
    }


}
