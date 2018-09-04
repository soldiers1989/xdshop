package com.xdshop.dal.domain;

public class UserShare {
    private String id;

	private String publishId;

	private String openId;

	private String posterOssKey;

	private String posterOssUrl;

	private Boolean fetchStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublishId() {
		return publishId;
	}

	public void setPublishId(String publishId) {
		this.publishId = publishId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getPosterOssKey() {
		return posterOssKey;
	}

	public void setPosterOssKey(String posterOssKey) {
		this.posterOssKey = posterOssKey;
	}

	public String getPosterOssUrl() {
		return posterOssUrl;
	}

	public void setPosterOssUrl(String posterOssUrl) {
		this.posterOssUrl = posterOssUrl;
	}

	public Boolean getFetchStatus() {
		return fetchStatus;
	}

	public void setFetchStatus(Boolean fetchStatus) {
		this.fetchStatus = fetchStatus;
	}

	
}