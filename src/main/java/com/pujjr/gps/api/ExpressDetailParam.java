package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

public class ExpressDetailParam {
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;
	
	@ApiModelProperty(value = "页码")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "每页显示数")
	private int pageSize = PAGE_SIZE;
	
	private String expressNo;

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	
	
}
