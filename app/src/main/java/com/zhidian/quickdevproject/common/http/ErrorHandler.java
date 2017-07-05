package com.zhidian.quickdevproject.common.http;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.blankj.utilcode.utils.SPUtils;
import com.zhidian.quickdevproject.common.constants.Constants;
import com.zhidian.quickdevproject.ui.base.IBaseView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorHandler {

    public static void handleError(Throwable e, IBaseView baseView) {
        Context context = null;
        if (baseView instanceof Activity) {
            context = (Context) baseView;
        }
        if (baseView instanceof Fragment) {
            context = ((Fragment) baseView).getActivity();
        }
        if (e instanceof ApiException) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            switch (((ApiException) e).getResultCode()) {
                case 401:
                    //账号在别处登录
                    new SPUtils(Constants.SP_FILE_NAME).clear();
                    //因为暂时没有登录页面，所以先随便填了一个Object.class
                    startLoginActivity(context, Object.class);
                    break;
                case 10031:
                    //token过期
                    new SPUtils(Constants.SP_FILE_NAME).clear();
                    //因为暂时没有登录页面，所以先随便填了一个Object.class
                    startLoginActivity(context, Object.class);
                    break;
                default:
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

    /**
     * 跳转到登录页面，同时清空之前的任务栈
     *
     * @param context    context
     * @param loginClazz 登录页面Activity的Class类
     */
    private static void startLoginActivity(Context context, Class loginClazz) {
        context.startActivity(new Intent(context, loginClazz).addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
