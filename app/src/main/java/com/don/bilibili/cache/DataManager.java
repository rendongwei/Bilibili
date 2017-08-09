package com.don.bilibili.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
	private static volatile DataManager mManager;

	public Map<String, List<String>> mLiveAreaTag = new HashMap<String, List<String>>();

	private DataManager() {
	}

	public static synchronized DataManager getInstance() {
		if (mManager == null) {
			mManager = new DataManager();
		}
		return mManager;
	}

}
