package com.don.bilibili.Model;

import com.don.bilibili.Json.Json;

import java.util.ArrayList;
import java.util.List;


public class HomeLiveCategory extends Json {

	private List<HomeLiveCategoryLive> lives = new ArrayList<HomeLiveCategoryLive>();
	private HomeLiveCategoryLivePartition partition;

	@Override
	public Object getEntity() {
		return this;
	}

	public List<HomeLiveCategoryLive> getLives() {
		return lives;
	}

	public void setLives(ArrayList<HomeLiveCategoryLive> lives) {
		this.lives = lives;
	}

	public HomeLiveCategoryLivePartition getPartition() {
		return partition;
	}

	public void setPartition(HomeLiveCategoryLivePartition partition) {
		this.partition = partition;
	}

}
