package com.zhidian.quickdevproject.common.http;


import com.zhidian.quickdevproject.common.entity.http.HttpResultEntity;
import com.zhidian.quickdevproject.common.entity.http.NewsEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/data/Android/{limit}/{pageNo}")
    Observable<HttpResultEntity<List<NewsEntity>>> getGankNews();

}
