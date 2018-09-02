package com.xdshop.vo;

import java.net.URL;

public class OssBaseVo {
	private String endpoint;
	private String accessKeyId;
	private String accessKeySecret;
	private String bucketName;
	private URL fileScanUrl;
	private String ossKey;
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public URL getFileScanUrl() {
		return fileScanUrl;
	}
	public void setFileScanUrl(URL fileScanUrl) {
		this.fileScanUrl = fileScanUrl;
	}
	public String getOssKey() {
		return ossKey;
	}
	public void setOssKey(String ossKey) {
		this.ossKey = ossKey;
	}
	

	
	
}
