package com.zhidian.quickdevproject.common.http.transformer;


import com.zhidian.quickdevproject.common.entity.http.HttpResultEntity;
import com.zhidian.quickdevproject.common.http.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ErrorCheckTransformer<T> implements ObservableTransformer<HttpResultEntity<T>, T> {

    @Override
    public Observable<T> apply(Observable<HttpResultEntity<T>> httpResultObservable) {
        return httpResultObservable.map(new Function<HttpResultEntity<T>, T>() {
            @Override
            public T apply(HttpResultEntity<T> httpResult) {
                if (!httpResult.isError()) {
                    throw new ApiException(httpResult.getCode(), httpResult.getMessage());
                }
                return httpResult.getResults();
            }
        });
    }
}
