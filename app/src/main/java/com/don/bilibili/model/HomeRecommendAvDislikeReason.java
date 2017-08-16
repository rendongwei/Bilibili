package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class HomeRecommendAvDislikeReason extends Json {

	@Name(name = "reason_id")
	private int id;
	@Name(name = "reason_name")
	private String name;

	@Override
	public Object getEntity() {
		return this;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
