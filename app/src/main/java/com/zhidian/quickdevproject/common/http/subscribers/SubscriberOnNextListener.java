package com.zhidian.quickdevproject.common.http.subscribers;

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
