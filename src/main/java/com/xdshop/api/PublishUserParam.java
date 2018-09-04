package com.xdshop.api;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class PublishUserParam extends BaseParam{

	private String name;
	private String mobile;
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
