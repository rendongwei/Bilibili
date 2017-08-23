package com.don.bilibili.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bilibili.nativelibrary.LibBili;
import com.bilibili.nativelibrary.SignedQuery;

import java.util.HashMap;

public class SignService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onCreate");
        HashMap<String, String> map = new HashMap<>();
        map.put("_device", "android");
        map.put("_hwid", "9edc79b18c3cf6f9");
        map.put("access_key", "bda109fc53f39041fab6cbe2bd043ec1");
        map.put("appkey", "1d8b6e7d45233436");
        map.put("build", "508000");
        map.put("mobi_app", "android");
        map.put("platform", "android");
        map.put("room_id", "10248");
        map.put("src", "bili");
        map.put("trace_id", "20170707090700025");
        map.put("ts", "1499389645");
        map.put("version", "5.8.0.508000");
        SignedQuery query = LibBili.a(map);
        if (query == null) {
            System.out.println("null");
        } else {
            System.out.println("签名:" + query.getSign() + "");
            Intent i = new Intent("aaaa");
            i.putExtra("sign", query.getSign());
            sendBroadcast(i);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
