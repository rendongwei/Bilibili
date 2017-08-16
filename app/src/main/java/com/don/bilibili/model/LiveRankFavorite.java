package com.don.bilibili.model;


import com.don.bilibili.Json.Json;

public class LiveRankFavorite extends Json {

	private int uid;
	private int rank;
	private int score;
	private String uname;
	private int level;
	private String medalName;
	private int color;
	private String face;

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

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMedalName() {
		return medalName;
	}

	public void setMedalName(String medalName) {
		this.medalName = medalName;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

}
