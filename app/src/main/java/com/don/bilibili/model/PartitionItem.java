package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class PartitionItem extends Json {

	private String title;
	private String cover;
	private String uri;
	private String param;
	@Name(name = "goto")
	private String goTo;
	private int play;
	private int danmaku;

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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getGoTo() {
		return goTo;
	}

	public void setGoTo(String goTo) {
		this.goTo = goTo;
	}

	public int getPlay() {
		return play;
	}

	public void setPlay(int play) {
		this.play = play;
	}

	public int getDanmaku() {
		return danmaku;
	}

	public void setDanmaku(int danmaku) {
		this.danmaku = danmaku;
	}

}
