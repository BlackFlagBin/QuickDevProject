package com.blackflagbin.common.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.blackflagbin.common.base.IApiException;
import com.blackflagbin.common.base.IBaseView;
import com.blackflagbin.common.facade.CommonLibrary;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorHandler {

    public static void handleError(Throwable e, IBaseView baseView) {
        Activity context = null;
        if (baseView instanceof Activity) {
            context = (Activity) baseView;
        }
        if (baseView instanceof Fragment) {
            context = ((Fragment) baseView).getActivity();
        }
        if (e instanceof IApiException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            switch (((IApiException) e).getResultCode()) {
                case 401:
                    //账号在别处登录
                    if (CommonLibrary.getInstance().getOnTokenExpiredListener() != null) {
                        CommonLibrary.getInstance().getOnTokenExpiredListener().onTokenExpired();
                    }
                    break;
                case 10031:
                    //token过期
                    if (CommonLibrary.getInstance().getOnTokenExpiredListener() != null) {
                        CommonLibrary.getInstance().getOnTokenExpiredListener().onTokenExpired();
                    }
                    break;
                default:
                    break;
            }
        } else {
            if (e instanceof SocketTimeoutException) {
                baseView.showTip("网络请求超时，请检查您的网络状态");
            } else if (e instanceof ConnectException) {
                baseView.showTip("网络中断，请检查您的网络状态");
            } else if (e instanceof UnknownHostException) {
                baseView.showTip("网络异常，请检查您的网络状态");
            } else {
                e.printStackTrace();
                baseView.showTip("出现了未知错误，请尝试从新打开App或者向我们反馈");
            }
        }
    }

    /**
     * 跳转到登录页面，同时清空之前的任务栈
     *
     * @param context    context
     * @param loginClazz 登录页面Activity的Class类
     */
    private static void startLoginActivity(Context context, Class loginClazz) {
        context.startActivity(new Intent(context, loginClazz).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
