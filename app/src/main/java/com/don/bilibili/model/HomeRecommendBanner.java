package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class HomeRecommendBanner extends Json {

	private int id;
	private String title;
	private String image;
	private String hash;
	private String uri;
	@Name(name = "request_id")
	private String requestId;
	@Name(name = "creative_id")
	private String creativeId;
	@Name(name = "src_id")
	private String srcId;
	@Name(name = "is_ad")
	private String isAd;
	@Name(name = "is_ad_loc")
	private String isAdLoc;
	@Name(name = "ad_cb")
	private String adCb;
	@Name(name = "click_url")
	private String clickUrl;
	@Name(name = "server_type")
	private int serverType;
	@Name(name = "resource_id")
	private int resourceId;
	@Name(name = "index")
	private int index;
	@Name(name = "cm_mark")
	private int cmMark;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCreativeId() {
		return creativeId;
	}

	public void setCreativeId(String creativeId) {
		this.creativeId = creativeId;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getIsAd() {
		return isAd;
	}

	public void setIsAd(String isAd) {
		this.isAd = isAd;
	}

	public String getIsAdLoc() {
		return isAdLoc;
	}

	public void setIsAdLoc(String isAdLoc) {
		this.isAdLoc = isAdLoc;
	}

	public String getAdCb() {
		return adCb;
	}

	public void setAdCb(String adCb) {
		this.adCb = adCb;
	}

	public String getClickUrl() {
		return clickUrl;
	}

	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getCmMark() {
		return cmMark;
	}

	public void setCmMark(int cmMark) {
		this.cmMark = cmMark;
	}

}
