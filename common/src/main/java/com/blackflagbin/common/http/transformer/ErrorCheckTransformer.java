package com.blackflagbin.common.http.transformer;

import com.blackflagbin.common.http.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class ErrorCheckTransformer<T> implements ObservableTransformer<IHttpResultEntity<T>, T> {

    @Override
    public Observable<T> apply(Observable<IHttpResultEntity<T>> httpResultObservable) {
        return httpResultObservable.map(new Function<IHttpResultEntity<T>, T>() {
            @Override
            public T apply(IHttpResultEntity<T> httpResult) {
                if (!httpResult.isSuccess()) {
                    throw new ApiException(httpResult.getErrorCode(), httpResult.getErrorMessage());
                }

                if (httpResult.getResult() == null) {
                    return (T) new Object();
                } else {
                    return httpResult.getResult();
                }
            }
        });
    }
}
