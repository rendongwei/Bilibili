package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApiService {

    @GET("AppNewIndex/common")
    Call<JSONObject> getLiveCommon(@Query("_device") String device, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("scale") String scale, @Query("ts") String ts, @Query("sign") String sign);


    @GET("AppNewIndex/recommend")
    Call<JSONObject> getLiveRecommend(@Query("_device") String device,@Query("access_key") String accessKey, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("scale") String scale, @Query("ts") String ts, @Query("sign") String sign);

}
