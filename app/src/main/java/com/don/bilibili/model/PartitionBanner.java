package com.don.bilibili.model;


import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

public class PartitionBanner extends Json {

	private int id;
	private String title;
	private String image;
	private String hash;
	private String uri;
	@Name(name = "resource_id")
	private int resourceId;
	@Name(name = "request_id")
	private int requestId;
	private int index;
	@Name(name = "server_type")
	private int serverType;

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

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getServerType() {
		return serverType;
	}

	public void setServerType(int serverType) {
		this.serverType = serverType;
	}

}
