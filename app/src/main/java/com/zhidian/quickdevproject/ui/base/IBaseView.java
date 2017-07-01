package com.zhidian.quickdevproject.ui.base;

/**
 * Created by blackflagbin on 2017/6/28.
 */

public interface IBaseView<T> {
    void showTip(String tipMsg);

    void showLoading();

    void dismissLoading();

    void showSuccessView();

    void showEmptyView();

    void showErrorView(String errorMsg);

    /**
     * 数据加载完毕后设置view
     *
     * @param data 从网络或本地获取的数据，通常不会是一个接口返回的数据，我们可以把多个接口返回的数据封装成一个包含多种数据的数据类
     */
    void setupView(T data);
}
