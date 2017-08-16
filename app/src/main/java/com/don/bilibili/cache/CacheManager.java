package com.don.bilibili.cache;

import com.don.bilibili.utils.EmptyUtil;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class CacheManager {

    private static volatile CacheManager mManager;

    private WeakReference<Map<String, List<String>>> mLiveAreaTagCache;

    private CacheManager() {
    }

    public static synchronized CacheManager getInstance() {
        if (mManager == null) {
            mManager = new CacheManager();
        }
        return mManager;
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
