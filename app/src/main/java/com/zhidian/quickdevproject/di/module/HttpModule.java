package com.zhidian.quickdevproject.di.module;

import com.blankj.utilcode.utils.SPUtils;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.zhidian.quickdevproject.BuildConfig;
import com.zhidian.quickdevproject.common.constants.Constants;
import com.zhidian.quickdevproject.common.http.ApiService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by blackflagbin on 2017/6/28.
 */
@Module
public class HttpModule {
    //默认超时时间
    public static final long DEFAULT_MILLISECONDS = 15;

    @Singleton
    @Provides
    public ApiService provideApiService(OkHttpClient client) {

        return new Retrofit.Builder().client(client)
                .baseUrl(BuildConfig.APP_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_MILLISECONDS, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader(Constants.TOKEN,
                                        new SPUtils(Constants.SP_FILE_NAME).getString(
                                                Constants.TOKEN, ""))
                                .build();
                        return chain.proceed(request);
                    }

                })
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }


}
