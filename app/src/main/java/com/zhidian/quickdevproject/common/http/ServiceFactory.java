package com.zhidian.quickdevproject.common.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhidian.quickdevproject.BuildConfig;
import com.zhidian.quickdevproject.common.utils.OkHttpUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceFactory {
    private static OkHttpClient sClient = OkHttpUtil.getOkHttpClient();

    public static <T> T createUserService(Class<T> serviceClazz) {
        return new Retrofit.Builder().client(sClient)
                .baseUrl(BuildConfig.APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(serviceClazz);
    }
}
