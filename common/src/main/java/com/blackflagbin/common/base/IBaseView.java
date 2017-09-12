package com.blackflagbin.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public interface IBaseView<T> {
    void startActivity(String url,@Nullable Bundle bundle);

    void finishActivity();

    void showTip(String tipMsg);

    void showLoading();

    void dismissLoading();

    /**
     * 数据加载完毕后设置view
     *
     * @param data 从网络或本地获取的数据，通常不会是一个接口返回的数据，我们可以把多个接口返回的数据封装成一个包含多种数据的数据类
     */
    void showSuccessView(T data);

    void showEmptyView();

    void showErrorView(String errorMsg);
}
