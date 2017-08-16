package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApiService {

    @GET
    Call<JSONObject> getUrl(@Url String url);

    @GET("appUser/getTitle")
    Call<JSONObject> getLiveMessageTitle(@Query("_device") String device, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("scale") String scale, @Query("ts") String ts, @Query("sign") String sign);
}
