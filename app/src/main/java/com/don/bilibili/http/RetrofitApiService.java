package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApiService {

    @GET
    Call<JSONObject> getUrl(@Url String url);

}
