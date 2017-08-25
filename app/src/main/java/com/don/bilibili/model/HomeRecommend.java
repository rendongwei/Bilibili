package com.don.bilibili.model;

import com.don.bilibili.Json.Json;

public class HomeRecommend extends Json{

	@Override
	public Object getEntity() {
		return this;
	}

	private Type type;
	private HomeRecommendAv av;
	private HomeRecommendBanner banner;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public HomeRecommendAv getAv() {
		return av;
	}

	public void setAv(HomeRecommendAv av) {
		this.av = av;
	}

	public HomeRecommendBanner getBanner() {
		return banner;
	}

	public void setBanner(HomeRecommendBanner banner) {
		this.banner = banner;
	}

	public enum Type {
		AV, BANNER
	}

}
