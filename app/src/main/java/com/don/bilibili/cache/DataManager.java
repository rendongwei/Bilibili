package com.don.bilibili.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    private static volatile DataManager mManager;
    private CacheManager mCacheManager;

    private DataManager() {
        mCacheManager = CacheManager.getInstance();
    }

    public static synchronized DataManager getInstance() {
        if (mManager == null) {
            mManager = new DataManager();
        }
        return mManager;
    }

    public Map<String, List<String>> getLiveAreaTagCache() {
        Map<String, List<String>> cache = mCacheManager.getLiveAreaTagCache();
        if (cache == null) {
            cache = new HashMap<>();
        }
        return cache;
    }

    public void setLiveAreaTagCache(Map<String, List<String>> cache) {
        mCacheManager.setLiveAreaTagCache(cache);
    }
}
