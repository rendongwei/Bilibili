package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

public class HomeRecommendAvTagCount extends Json {

	private int atten;

	@Override
	public Object getEntity() {
		return this;
	}

	public int getAtten() {
		return atten;
	}

	public void setAtten(int atten) {
		this.atten = atten;
	}

}
