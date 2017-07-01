package com.zhidian.quickdevproject.common.http;

import android.content.Context;
import android.widget.Toast;

import com.zhidian.quickdevproject.common.utils.SharedPreferencesUtils;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorHandler {
    public static void handleError(
            Throwable e, Context context, IBaseView baseView) {
        if (e instanceof ApiException) {
            switch (((ApiException) e).getResultCode()) {
                case 401:
                    Toast.makeText(context, "您的账号在别处登录，请重新登录", Toast.LENGTH_SHORT).show();
                    SharedPreferencesUtils.clear();
                    break;
                default:
                    Toast.makeText(context, ((ApiException) e).getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    break;
            }
        } else {
            if (e instanceof SocketTimeoutException) {
                baseView.showErrorView("网络请求超时，请检查您的网络状态");
            } else if (e instanceof ConnectException) {
                baseView.showErrorView("网络中断，请检查您的网络状态");

            } else if (e instanceof UnknownHostException) {
                baseView.showErrorView("网络异常，请检查您的网络状态");

            } else {
                baseView.showErrorView("未知错误");

            }
        }
    }
}
