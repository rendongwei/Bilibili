package com.don.bilibili.cache;

import com.don.bilibili.utils.EmptyUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheManager {

    private static volatile CacheManager mManager;

    private WeakReference<Map<String, String>> mSignCache;

    private WeakReference<Map<String, List<String>>> mLiveAreaTagCache;

    private CacheManager() {
    }

    public static synchronized CacheManager getInstance() {
        if (mManager == null) {
            mManager = new CacheManager();
        }
        return mManager;
    }


    public String getSignCache(String key) {
        if (mSignCache != null) {
            return mSignCache.get().get(key);
        }
        return null;
    }

    public void setSignCache(String key, String sign) {
        Map<String, String> cache = null;
        if (mSignCache == null || mSignCache.get() == null) {
            cache = new HashMap<>();
        } else {
            cache = mSignCache.get();
        }
        cache.put(key, sign);
        mSignCache = new WeakReference<Map<String, String>>(cache);
    }

    public Map<String, List<String>> getLiveAreaTagCache() {
        if (mLiveAreaTagCache != null) {
            return mLiveAreaTagCache.get();
        }
        return null;
    }

    public void setLiveAreaTagCache(Map<String, List<String>> cache) {
        if (mLiveAreaTagCache != null) {
            mLiveAreaTagCache.clear();
            mLiveAreaTagCache = null;
        }
        if (EmptyUtil.isEmpty(cache)) {
            mLiveAreaTagCache = null;
            return;
        }
        mLiveAreaTagCache = new WeakReference<Map<String, List<String>>>(cache);
    }
}
