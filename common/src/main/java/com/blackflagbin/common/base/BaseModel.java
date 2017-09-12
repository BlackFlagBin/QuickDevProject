package com.blackflagbin.common.base;

import com.blackflagbin.common.http.HttpProvider;
import com.blankj.utilcode.util.SPUtils;

/**
 * Created by blackflagbin on 2017/9/11.
 */

public class BaseModel<A > {
    protected A       mApiService = (A) HttpProvider.getInstance().provideApiService();
    protected SPUtils mSPUtils    = SPUtils.getInstance();
}
