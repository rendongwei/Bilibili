package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitApiService {

    @GET
    Call<JSONObject> getUrl(@Url String url);

    @GET("AppIndex/dynamic")
    Call<JSONObject> getLiveCategoryRefresh(@Query("_device") String device, @Query("appkey") String key, @Query("area") String area, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("ts") String ts, @Query("sign") String sign);

    @GET("mobile/rooms")
    Call<JSONObject> getLiveAll(@Query("_device") String device, @Query("_hwid") String hwid, @Query("appkey") String key, @Query("area_id") int area, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("page") int page, @Query("platform") String platform, @Query("sort") String sort, @Query("src") String src, @Query("trace_id") String trace_id, @Query("ts") String ts, @Query("version") String version, @Query("sign") String sign);

    @GET("AppIndex/tags")
    Call<JSONObject> getLiveTag(@Query("_device") String device, @Query("_hwid") String hwid, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("src") String src, @Query("trace_id") String trace_id, @Query("ts") String ts, @Query("version") String version, @Query("sign") String sign);

    @GET("mobile/rooms")
    Call<JSONObject> getLiveArea(@Query("_device") String device, @Query("_hwid") String hwid, @Query("appkey") String key, @Query("area_id") int areaId, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("page") int page, @Query("platform") String platform, @Query("sort") String sort, @Query("src") String src, @Query("trace_id") String trace_id, @Query("ts") String ts, @Query("version") String version, @Query("sign") String sign, @Query("tag") String tag);

    @GET("AppRoom/index")
    Call<JSONObject> getLiveInfo(@Query("_device") String device, @Query("appkey") String key, @Query("build") String build, @Query("buld") String buld, @Query("jumpFrom") String jumpFrom, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("room_id") int room_id, @Query("scale") String scale, @Query("ts") String ts, @Query("sign") String sign);

    @GET("AppRoom/msg")
    Call<JSONObject> getLiveMessage(@Query("_device") String device, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("room_id") int room_id, @Query("ts") String ts, @Query("sign") String sign);

    @GET("appUser/getTitle")
    Call<JSONObject> getLiveMessageTitle(@Query("_device") String device, @Query("appkey") String key, @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("scale") String scale, @Query("ts") String ts, @Query("sign") String sign);

    @GET("live/getRoundPlayVideo")
    Call<JSONObject> getLiveRoundInfo(@Query("_device") String device, @Query("_hwid") String hwid, @Query("appkey") String key,  @Query("build") String build, @Query("mobi_app") String mobi_app, @Query("platform") String platform, @Query("room_id") int room_id, @Query("src") String src, @Query("trace_id") String trace_id, @Query("ts") String ts, @Query("version") String version, @Query("sign") String sign);

    @GET
    Call<JSONObject> getLiveRoundPlayUrl(@Url String url);
}
