package com.blackflagbin.common.http.transformer;



import com.blackflagbin.common.entity.http.HttpResultEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DefaultTransformer<T> implements ObservableTransformer<HttpResultEntity<T>, T> {

    @Override
    public Observable<T> apply(Observable<HttpResultEntity<T>> observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .compose(new ErrorCheckTransformer<T>())
                .observeOn(AndroidSchedulers.mainThread());
    }
}