package com.zhidian.quickdevproject.common.http.subscribers;

public interface SubscriberCallBack<T> {
    void onNext(T t);
    void onError(Throwable e);

}
