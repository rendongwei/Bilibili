package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class HomeEpisode extends Json {

	private String badge;
	private String cover;
	private int favourites;
	@Name(name = "is_auto")
	private boolean isAuto;
	@Name(name = "is_finish")
	private boolean isFinish;
	@Name(name = "is_started")
	private boolean isStarted;
	@Name(name = "last_time")
	private long lastTime;
	@Name(name = "newest_ep_index")
	private String newestEpIndex;
	@Name(name = "pub_time")
	private long pubTime;
	@Name(name = "season_id")
	private int seasonId;
	@Name(name = "season_status")
	private int seasonStatus;
	@Name(name = "title")
	private String title;
	@Name(name = "total_count")
	private int totalCount;
	@Name(name = "watching_count")
	private int watchingCount;

	@Override
	public Object getEntity() {
		return this;
	}

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public int getFavourites() {
		return favourites;
	}

	public void setFavourites(int favourites) {
		this.favourites = favourites;
	}

	public boolean isAuto() {
		return isAuto;
	}

	public void setAuto(boolean isAuto) {
		this.isAuto = isAuto;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public String getNewestEpIndex() {
		return newestEpIndex;
	}

	public void setNewestEpIndex(String newestEpIndex) {
		this.newestEpIndex = newestEpIndex;
	}

	public long getPubTime() {
		return pubTime;
	}

	public void setPubTime(long pubTime) {
		this.pubTime = pubTime;
	}

	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}

	public int getSeasonStatus() {
		return seasonStatus;
	}

	public void setSeasonStatus(int seasonStatus) {
		this.seasonStatus = seasonStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getWatchingCount() {
		return watchingCount;
	}

	public void setWatchingCount(int watchingCount) {
		this.watchingCount = watchingCount;
	}

}
