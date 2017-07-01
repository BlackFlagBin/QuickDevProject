package com.zhidian.quickdevproject.common.http;


import com.zhidian.quickdevproject.common.entity.http.HttpResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    /**
     * 获取门禁列表
     *
     * @return
     */
    @GET("user/entrance/{communityId}/list")
    Observable<HttpResult<List<Object>>> getEntranceList(
            @Path("communityId") String communityId);

    /**
     * 修改门禁不再接收呼叫
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/entrance/set_intercom_accept")
    Observable<HttpResult<String>> chooseNotCallEntrance(
            @Field("id") String deviceId,
            @Field("ifSupportVedioIntercom") int ifSupportVedioIntercom);

    /**
     * 检查门禁是否过期
     *
     * @return
     */
    @GET("user/entrance/{entranceId}/unlock_in_app")
    Observable<HttpResult<Object>> openLockCheck(@Path("entranceId") String entranceId);

    /**
     * 退出登录
     *
     * @return
     */
    @POST("account/logout")
    Observable<HttpResult<String>> logout();
}
