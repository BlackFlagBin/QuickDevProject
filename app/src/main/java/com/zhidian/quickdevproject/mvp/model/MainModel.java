package com.zhidian.quickdevproject.mvp.model;

import com.zhidian.quickdevproject.common.entity.http.NewsEntity;
import com.zhidian.quickdevproject.common.http.ApiService;
import com.zhidian.quickdevproject.common.http.transformer.DefaultTransformer;
import com.zhidian.quickdevproject.mvp.contract.MainContract;

import java.util.List;

import javax.crypto.MacSpi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.Retrofit;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public class MainModel implements MainContract.Model {

    private final ApiService mApiService;

    public MainModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<List<NewsEntity>> getGankNews() {
        return mApiService.getGankNews().compose(new DefaultTransformer<List<NewsEntity>>());
    }
}
