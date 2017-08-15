package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

public class HomeLiveBanner extends Json {

	private String title;
	private String img;
	private String remark;
	private String link;

	@Override
	public Object getEntity() {
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
