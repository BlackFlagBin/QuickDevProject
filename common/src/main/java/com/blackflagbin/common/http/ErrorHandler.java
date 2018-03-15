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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class ErrorHandler {

    public static void handleError(final Throwable e, final IBaseView baseView) {
        Activity context = null;
        if (baseView instanceof Activity) {
            context = (Activity) baseView;
        }
        if (baseView instanceof Fragment) {
            context = ((Fragment) baseView).getActivity();
        }
        if (e instanceof IApiException) {
            final Activity finalContext = context;
            Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    Toast.makeText(finalContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            int resultCode = ((IApiException) e).getResultCode();
            if (resultCode == CommonLibrary.getInstance().getTokenExpiredErrorCode()) {
                //token过期
                if (CommonLibrary.getInstance().getOnTokenExpiredListener() != null) {
                    CommonLibrary.getInstance().getOnTokenExpiredListener().onTokenExpired();
                }
            } else if (resultCode == CommonLibrary.getInstance().getTokenInvalidErrorCode()) {
                //token无效
                if (CommonLibrary.getInstance().getOnTokenExpiredListener() != null) {
                    CommonLibrary.getInstance().getOnTokenExpiredListener().onTokenExpired();
                }
            }
        } else {
            if (e instanceof SocketTimeoutException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        baseView.showTip("网络请求超时，请检查您的网络状态");
                    }
                });
            } else if (e instanceof ConnectException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        baseView.showTip("网络中断，请检查您的网络状态");
                    }
                });
            } else if (e instanceof UnknownHostException) {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        baseView.showTip("网络异常，请检查您的网络状态");
                    }
                });
            } else {
                Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        baseView.showTip("出现了未知错误，请尝试从新打开App或者向我们反馈");
                    }
                });
                e.printStackTrace();
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
