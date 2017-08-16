package com.don.bilibili.model;

import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

import java.util.ArrayList;
import java.util.List;


public class HomeRecommendAv extends Json {

	private String title;
	private String cover;
	private String uri;
	private String param;
	@Name(name = "goto")
	private String type;
	private String desc;
	private int play;
	private int danmuku;
	private int reply;
	private int favorite;
	private int coin;
	private int share;
	private int idx;
	private int tid;
	private String tname;
	private int ctime;
	private int duration;
	private int mid;
	private String name;
	private String face;
	private HomeRecommendAvTag tag;
	@Name(name = "dislike_reasons")
	private List<HomeRecommendAvDislikeReason> reasons = new ArrayList<HomeRecommendAvDislikeReason>();

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getPlay() {
		return play;
	}

	public void setPlay(int play) {
		this.play = play;
	}

	public int getDanmuku() {
		return danmuku;
	}

	public void setDanmuku(int danmuku) {
		this.danmuku = danmuku;
	}

	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	public int getFavorite() {
		return favorite;
	}

	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getCtime() {
		return ctime;
	}

	public void setCtime(int ctime) {
		this.ctime = ctime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public HomeRecommendAvTag getTag() {
		return tag;
	}

	public void setTag(HomeRecommendAvTag tag) {
		this.tag = tag;
	}

	public List<HomeRecommendAvDislikeReason> getReasons() {
		return reasons;
	}

	public void setReasons(List<HomeRecommendAvDislikeReason> reasons) {
		this.reasons = reasons;
	}

}
