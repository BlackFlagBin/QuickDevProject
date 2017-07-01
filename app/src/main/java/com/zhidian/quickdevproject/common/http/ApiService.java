package com.zhidian.quickdevproject.common.http;


import com.zhidian.quickdevproject.common.entity.http.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param pwd      用户密码
     * @return 用户信息
     */
    @FormUrlEncoded
    @POST("account/login")
    Observable<HttpResult<Object>> login(
            @Field("userName") String userName, @Field("pwd") String pwd);

    /**
     * 获取用户小区列表
     *
     * @return
     */
    @GET("user/community/list")
    Observable<HttpResult<List<Object>>> getCommunityList();

    @GET("/")
    Observable<String> getDataFromBaidu();


    /**
     * 退出登录
     *
     * @return
     */
    @POST("account/logout")
    Observable<HttpResult<String>> logout();
}
