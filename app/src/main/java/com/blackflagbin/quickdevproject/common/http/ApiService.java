package com.blackflagbin.quickdevproject.common.http;

import com.blackflagbin.common.http.transformer.IHttpResultEntity;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.blackflagbin.quickdevproject.common.entity.http.HttpResultEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by blackflagbin on 2017/9/11.
 */

public interface ApiService {

    @GET("/api/data/福利/10/{pageNo}")
    Observable<HttpResultEntity<List<Entity>>> getDataList(@Path("pageNo") int pageNo);
}
