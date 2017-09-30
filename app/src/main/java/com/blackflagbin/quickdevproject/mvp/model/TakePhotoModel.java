package com.blackflagbin.quickdevproject.mvp.model;

import com.blackflagbin.common.base.BaseModel;
import com.blackflagbin.common.http.transformer.DefaultTransformer;
import com.blackflagbin.quickdevproject.common.entity.http.Entity;
import com.blackflagbin.quickdevproject.common.http.ApiService;
import com.blackflagbin.quickdevproject.mvp.contract.MainContract;
import com.blackflagbin.quickdevproject.mvp.contract.TakePhotoContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by blackflagbin on 2017/9/12.
 */

public class TakePhotoModel extends BaseModel<ApiService> implements TakePhotoContract.ITakePhotoModel {

}
