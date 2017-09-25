package com.blackflagbin.common.http.transformer;

/**
 * Created by blackflagbin on 2017/9/25.
 */

public interface IHttpResultEntity<T> {
    boolean isSuccess();

    int getErrorCode();

    String getErrorMessage();

    T getResult();
}
