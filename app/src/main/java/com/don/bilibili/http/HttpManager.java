package com.don.bilibili.http;

import retrofit2.Retrofit;

public class HttpManager {

    private static volatile HttpManager mManager;
    private Retrofit mApiRetrofit;

    private HttpManager() {
        mApiRetrofit = new Retrofit.Builder().baseUrl("http://api.live.bilibili.com").addConverterFactory(JsonObjectConverterFactory.create())
                .build();
    }

    public static synchronized HttpManager getInstance() {
        if (mManager == null) {
            mManager = new HttpManager();
        }
        return mManager;
    }

    public RetrofitApiService getApiSevice() {
        return mApiRetrofit.create(RetrofitApiService.class);
    }
}
