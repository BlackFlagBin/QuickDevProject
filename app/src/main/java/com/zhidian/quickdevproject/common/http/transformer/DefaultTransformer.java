package com.zhidian.quickdevproject.common.http.transformer;


import com.zhidian.quickdevproject.common.entity.http.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefaultTransformer<T> implements ObservableTransformer<HttpResult<T>, T> {

    @Override
    public Observable<T> apply(Observable<HttpResult<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(new ErrorCheckTransformer<T>())
                .observeOn(AndroidSchedulers.mainThread());
    }
}