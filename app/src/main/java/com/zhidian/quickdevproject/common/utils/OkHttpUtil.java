package com.zhidian.quickdevproject.common.utils;


import com.zhidian.quickdevproject.common.constants.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpUtil {
    public static final long DEFAULT_MILLISECONDS = 15;
    private volatile static OkHttpUtil             mInstance;
    private static          OkHttpClient           mOkHttpClient;
    private                 HttpLoggingInterceptor mInterceptor;

    public OkHttpUtil(OkHttpClient okHttpClient) {
        mInterceptor = new HttpLoggingInterceptor();
        mInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder().connectTimeout(
                    DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader(
                                            Constants.TOKEN,
                                            SharedPreferencesUtils.getString(Constants.TOKEN,
                                                    ""))
                                    .build();
                            return chain.proceed(request);
                        }

                    })
                    .addInterceptor(mInterceptor)
                    .build();

        } else {
            mOkHttpClient = okHttpClient;
        }

    }

    public static OkHttpUtil getInstance() {
        return initClient(null);
    }

    public static OkHttpUtil initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtil(okHttpClient);
                }
            }
        }

        return mInstance;
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpUtil.getInstance();
        return mOkHttpClient;
    }
}

