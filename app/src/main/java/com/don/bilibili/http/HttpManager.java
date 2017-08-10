package com.don.bilibili.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class HttpManager {

    private static volatile HttpManager mManager;
    private Retrofit mApiRetrofit;

    private HttpManager() {
        mApiRetrofit = new Retrofit.Builder().baseUrl("http://api.live.bilibili.com").addConverterFactory(JsonObjectConverterFactory.create()).client(getClient())
                .build();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Message", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        return client.retryOnConnectionFailure(true)
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
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
