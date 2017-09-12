package com.blackflagbin.quickdevproject.mvp.model;

import com.blackflagbin.common.base.BaseModel;
import com.blackflagbin.common.http.transformer.DefaultTransformer;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.blackflagbin.quickdevproject.common.http.ApiService;
import com.blackflagbin.quickdevproject.mvp.contract.MainContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public class MainModel extends BaseModel<ApiService> implements MainContract.IMainModel {
    @Override
    public Observable<List<Entity>> getDataList(int pageNo) {
        return mApiService.getDataList(pageNo).compose(new DefaultTransformer<List<Entity>>());
    }
}
