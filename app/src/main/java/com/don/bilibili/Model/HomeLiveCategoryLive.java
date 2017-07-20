package com.don.bilibili.Model;



import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;


public class HomeLiveCategoryLive extends Json {

	private HomeLiveCategoryLiveOwner owner;
	private Icon cover;
	@Name(name = "room_id")
	private int roomId;
	private int online;
	private String area;
	@Name(name = "area_id")
	private int areaId;
	private String title;
	@Name(name = "playurl")
	private String url;
	@Name(name = "accept_quality")
	private int quality;

	@Override
	public Object getEntity() {
		return this;
	}

	public HomeLiveCategoryLiveOwner getOwner() {
		return owner;
	}

	public void setOwner(HomeLiveCategoryLiveOwner owner) {
		this.owner = owner;
	}

	public Icon getCover() {
		return cover;
	}

	public void setCover(Icon cover) {
		this.cover = cover;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

}
