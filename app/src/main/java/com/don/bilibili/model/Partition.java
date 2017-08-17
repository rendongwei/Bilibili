package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

import java.util.ArrayList;
import java.util.List;

public class Partition extends Json {

	private int param;
	private String type;
	private String style;
	private String title;
	private List<PartitionItem> body = new ArrayList<PartitionItem>();
	private List<PartitionBanner> banners = new ArrayList<PartitionBanner>();

	@Override
	public Object getEntity() {
		return this;
	}

	public int getParam() {
		return param;
	}

	public void setParam(int param) {
		this.param = param;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PartitionItem> getBody() {
		return body;
	}

	public void setBody(List<PartitionItem> body) {
		this.body = body;
	}

	public List<PartitionBanner> getBanners() {
		return banners;
	}

	public void setBanners(List<PartitionBanner> banners) {
		this.banners = banners;
	}

}
