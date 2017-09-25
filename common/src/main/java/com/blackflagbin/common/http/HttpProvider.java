package com.blackflagbin.common.http;

import com.blackflagbin.common.facade.CommonLibrary;
import com.blackflagbin.common.http.interceptor.CookieInterceptor;
import com.blankj.utilcode.util.SPUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blackflagbin on 2017/9/11.
 */

public class HttpProvider {

    //默认超时时间
    public static final long DEFAULT_MILLISECONDS = 15;

    private HttpProvider() {}

    public static HttpProvider getInstance() {
        return InnerClass.instance;
    }

    public <P> P provideApiService() {

        return (P) new Retrofit.Builder().client(provideOkHttpClient())
                .baseUrl(CommonLibrary.getInstance().getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CommonLibrary.getInstance().getApiClass());
    }

    public <P> P provideApiService(String baseUrl) {

        return (P) new Retrofit.Builder().client(provideOkHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CommonLibrary.getInstance().getApiClass());
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("token", SPUtils.getInstance().getString("token", "")).build();
                        return chain.proceed(request);
                    }

                })
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new CookieInterceptor(true))
                .build();
    }

    private static class InnerClass {
        static HttpProvider instance = new HttpProvider();
    }

}
