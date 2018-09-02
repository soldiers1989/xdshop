package com.xdshop.api;

/**
 * 
 * @author Administrator
 *
 */
public class ShareParamVo {
	private String openId;
	//本次分享的发布ID
	private String publishId;
	private String name;
	private String mobile;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
	public String getPublishId() {
		return publishId;
	}
	public void setPublishId(String publishId) {
		this.publishId = publishId;
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
	
}
