package com.zhidian.quickdevproject.common.http.transformer;


import com.zhidian.quickdevproject.common.entity.http.HttpResult;
import com.zhidian.quickdevproject.common.http.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ErrorCheckTransformer<T> implements ObservableTransformer<HttpResult<T>, T> {

    @Override
    public Observable<T> apply(Observable<HttpResult<T>> httpResultObservable) {
        return httpResultObservable.map(new Function<HttpResult<T>, T>() {
            @Override
            public T apply(HttpResult<T> httpResult) {
                if (!httpResult.isSuccess()) {
                    throw new ApiException(httpResult.getCode(), httpResult.getMessage());
                }
                return httpResult.getData();
            }
        });
    }
}
