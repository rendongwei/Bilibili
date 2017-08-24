package com.don.bilibili.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bilibili.nativelibrary.LibBili;
import com.bilibili.nativelibrary.SignedQuery;
import com.don.bilibili.constants.Action;

import java.util.HashMap;
import java.util.TreeMap;

public class SignService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String method = intent.getStringExtra("method");
        Bundle bundle = intent.getBundleExtra("param");
        if (bundle != null) {
            HashMap<String, String> map = new HashMap<>();
            for (String key : bundle.keySet()) {
                map.put(key, bundle.get(key).toString());
            }
            TreeMap<String, String> treeMap = new TreeMap(map);
            SignedQuery query = LibBili.s(treeMap);

            Intent i = new Intent(Action.SIGN_ACITON);
            i.putExtra("method", method);
            i.putExtra("sign", method + query.toString());
            sendBroadcast(i);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
