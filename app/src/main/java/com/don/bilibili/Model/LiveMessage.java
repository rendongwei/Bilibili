package com.don.bilibili.Model;

import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

import java.util.ArrayList;
import java.util.List;


public class LiveMessage extends Json {

	private int uid;
	@Name(name = "nickname")
	private String name;
	@Name(name = "timeline")
	private String time;
	@Name(name = "text")
	private String message;
	@Name(name = "isadmin")
	private boolean isAdmin;
	@Name(name = "vip")
	private boolean isVip;
	@Name(name = "svip")
	private boolean isSvip;
	private List<String> medal = new ArrayList<String>();
	private List<String> title = new ArrayList<String>();
	@Name(name = "user_level")
	private List<String> level = new ArrayList<String>();
	private int rank;
	@Name(name = "teamid")
	private int teamId;
	private int rnd;
	@Name(name = "user_title")
	private String userTitle;
	@Name(name = "guard_level")
	private int guardLevel;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isVip() {
		return isVip;
	}

	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}

	public boolean isSvip() {
		return isSvip;
	}

	public void setSvip(boolean isSvip) {
		this.isSvip = isSvip;
	}

	public List<String> getMedal() {
		return medal;
	}

	public void setMedal(List<String> medal) {
		this.medal = medal;
	}

	public List<String> getTitle() {
		return title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getLevel() {
		return level;
	}

	public void setLevel(List<String> level) {
		this.level = level;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getRnd() {
		return rnd;
	}

	public void setRnd(int rnd) {
		this.rnd = rnd;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public int getGuardLevel() {
		return guardLevel;
	}

	public void setGuardLevel(int guardLevel) {
		this.guardLevel = guardLevel;
	}

}
