package com.don.bilibili.http;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpManager {

    private static volatile  HttpManager mManager;
    private Retrofit mApiRetrofit;

    private HttpManager(){
        mApiRetrofit = new Retrofit.Builder().baseUrl("http://api.live.bilibili.com").addConverterFactory(ScalarsConverterFactory.create())
                .build();
       Call<JSONObject> call =  mApiRetrofit.create(RetrofitService.class).getTest();
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                System.out.println(response.body().optInt("code"));
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    public static synchronized  HttpManager getInstance(){
        if (mManager == null){
           mManager = new HttpManager();
        }
        return  mManager;
    }
}
