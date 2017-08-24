package com.don.bilibili.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.bilibili.nativelibrary.LibBili;
import com.bilibili.nativelibrary.SignedQuery;
import com.don.bilibili.cache.DataManager;
import com.don.bilibili.constants.Action;
import com.don.bilibili.utils.EmptyUtil;
import com.don.bilibili.utils.EncryptUtil;

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
            StringBuffer buffer = new StringBuffer(method + ":");
            for (TreeMap.Entry<String, String> entry : treeMap.entrySet()) {
                buffer.append(entry.getKey() + "=" + entry.getValue()).append("&");
            }
            if (buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
            System.out.println("取sign的param:" + buffer);
            String cacheKey = EncryptUtil.getMD5(buffer.toString());
            String sign = DataManager.getInstance().getSignCache(cacheKey);
            if (EmptyUtil.isEmpty(sign)) {
                SignedQuery query = LibBili.s(treeMap);
                sign = query.getSign();
                if (!EmptyUtil.isEmpty(sign)) {
                    DataManager.getInstance().setSignCache(cacheKey, sign);
                }
                System.out.println("加密取的sign:" + sign);
            } else {
                System.out.println("缓存取的sign:" + sign);
            }
            Intent i = new Intent(Action.SIGN_ACITON);
            i.putExtra("method", method);
            i.putExtra("sign", EmptyUtil.isEmpty(sign) ? "" : sign);
            sendBroadcast(i);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
