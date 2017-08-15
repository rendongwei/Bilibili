package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

public class Icon extends Json {

	private String src;
	private int width;
	private int height;

	@Override
	public Object getEntity() {
		return this;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
