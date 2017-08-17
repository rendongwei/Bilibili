package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class HomeEpisodeFoot extends Json {

	private String cover;
	private long cursor;
	private String desc;
	private int id;
	@Name(name = "is_new")
	private boolean isNew;
	private String link;
	private String onDt;
	private int reply;
	private String title;

	@Override
	public Object getEntity() {
		return this;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public long getCursor() {
		return cursor;
	}

	public void setCursor(long cursor) {
		this.cursor = cursor;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getOnDt() {
		return onDt;
	}

	public void setOnDt(String onDt) {
		this.onDt = onDt;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
