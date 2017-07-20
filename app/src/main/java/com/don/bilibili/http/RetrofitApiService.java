package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiService {

    @GET("AppNewIndex/common")
    Call<JSONObject> getCommon();

}
