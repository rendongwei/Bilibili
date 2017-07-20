package com.don.bilibili.Model;

import com.don.libirary.json.Json;
import com.don.libirary.json.annotation.Name;

public class HomeLiveCategoryBanner extends Json {

	private HomeLiveCategoryLive live;
	private Icon cover;
	private String title;
	@Name(name = "is_clip")
	private boolean isClip;

	@Override
	public Object getEntity() {
		return this;
	}

	public HomeLiveCategoryLive getLive() {
		return live;
	}

	public void setLive(HomeLiveCategoryLive live) {
		this.live = live;
	}

	public Icon getCover() {
		return cover;
	}

	public void setCover(Icon cover) {
		this.cover = cover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isClip() {
		return isClip;
	}

	public void setClip(boolean isClip) {
		this.isClip = isClip;
	}

}
