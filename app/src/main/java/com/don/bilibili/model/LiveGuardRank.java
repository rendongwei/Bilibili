package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class LiveGuardRank extends Json {

	private int uid;
	private int ruid;
	private int rank;
	@Name(name = "username")
	private String userName;
	private String face;
	@Name(name = "guard_level")
	private int level;

	@Override
	public Object getEntity() {
		return this;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getRuid() {
		return ruid;
	}

	public void setRuid(int ruid) {
		this.ruid = ruid;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
