package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsStoreBranch;

import io.swagger.annotations.ApiModelProperty;

public class StoreBranchVo extends GpsStoreBranch{
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;
	@ApiModelProperty(value = "当前页")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "页码大小")
	private int pageSize = PAGE_SIZE;

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
	
	
}
