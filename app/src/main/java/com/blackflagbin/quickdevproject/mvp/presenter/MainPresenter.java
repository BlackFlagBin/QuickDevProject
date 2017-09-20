package com.blackflagbin.quickdevproject.mvp.presenter;

import com.blackflagbin.common.base.BasePresenter;
import com.blackflagbin.common.entity.http.HttpResultEntity;
import com.blackflagbin.common.http.subscribers.NoProgressObserver;
import com.blackflagbin.common.http.subscribers.ObserverCallBack;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.blackflagbin.quickdevproject.mvp.contract.MainContract;
import com.blackflagbin.quickdevproject.mvp.model.MainModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public class MainPresenter extends BasePresenter<MainContract.IMainModel, MainContract.IMainView> implements MainContract.IMainPresenter {


    public MainPresenter(MainContract.IMainView iMainView) {
        super(iMainView);
    }

    @Override
    protected MainContract.IMainModel getModel() {
        return new MainModel();
    }

    @Override
    public Disposable initData(int pageNo) {
        String url = "http://gank.io/api/data/" + URLEncoder.encode("福利") + "/10/" + pageNo;
        if (pageNo == 1) {
            mView.beforeInitData();
            return mModel.getDataList(pageNo)
                    .subscribeWith(new NoProgressObserver<List<Entity>>(true, false, url, mView, new ObserverCallBack<List<Entity>>() {
                        @Override
                        public void onNext(List<Entity> list) {
                            mView.showSuccessView(list);
                            mView.dismissLoading();
                        }

                        @Override
                        public void onCacheNext(String dataString) {
                            Type jsonType = new TypeToken<HttpResultEntity<List<Entity>>>() {
                            }.getType();
                            HttpResultEntity<List<Entity>> resultEntity = new Gson().fromJson(dataString, jsonType);
                            mView.showSuccessView(resultEntity.getResults());
                            mView.dismissLoading();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.dismissLoading();
                            mView.showErrorView("网络异常");
                        }
                    }));
        } else {
            return mModel.getDataList(pageNo)
                    .subscribeWith(new NoProgressObserver<List<Entity>>(true, true, url, mView, new ObserverCallBack<List<Entity>>() {
                        @Override
                        public void onNext(List<Entity> list) {
                            mView.afterLoadMore(list);
                        }

                        @Override
                        public void onCacheNext(String dataString) {
                            Type jsonType = new TypeToken<HttpResultEntity<List<Entity>>>() {
                            }.getType();
                            HttpResultEntity<List<Entity>> resultEntity = new Gson().fromJson(dataString, jsonType);
                            mView.afterLoadMore(resultEntity.getResults());
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    }));
        }


    }

    @Override
    public Disposable initData() {
        return initData(1);
    }
}
