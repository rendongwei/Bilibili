package com.don.bilibili.Model;

import com.don.libirary.json.Json;

public class HomeLiveCategoryLiveOwner extends Json {

	private int mid;
	private String name;
	private String face;

	@Override
	public Object getEntity() {
		return this;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

}
