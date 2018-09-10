package com.xdshop.dal.domain;

import java.util.Date;

public class User {
    private String id;

	private String openId;

	private String nickName;

	private String headerOssKey;

	private String headerUrl;

	private String qrCodeOssKey;

	private String qrCodeUrl;

	private String parentOpenId;

	private String name;

	private String mobile;

	private String posterOssKey;

	private String posterUrl;

	private Boolean subType;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeaderOssKey() {
		return headerOssKey;
	}

	public void setHeaderOssKey(String headerOssKey) {
		this.headerOssKey = headerOssKey;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String getQrCodeOssKey() {
		return qrCodeOssKey;
	}

	public void setQrCodeOssKey(String qrCodeOssKey) {
		this.qrCodeOssKey = qrCodeOssKey;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getParentOpenId() {
		return parentOpenId;
	}

	public void setParentOpenId(String parentOpenId) {
		this.parentOpenId = parentOpenId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPosterOssKey() {
		return posterOssKey;
	}

	public void setPosterOssKey(String posterOssKey) {
		this.posterOssKey = posterOssKey;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public Boolean getSubType() {
		return subType;
	}

	public void setSubType(Boolean subType) {
		this.subType = subType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



}