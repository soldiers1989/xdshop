package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class ApplybackSearchParam {
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;

	@ApiModelProperty(value = "经销商编码")
	private String branchId;

	@ApiModelProperty(value = "页码")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "每页显示数")
	private int pageSize = PAGE_SIZE;

	/**
	 * @return branchCode
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchCode
	 *            要设置的 branchCode
	 */
	public void setBranchId(String branchCode) {
		this.branchId = branchCode;
	}

	/**
	 * @return pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum
	 *            要设置的 pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            要设置的 pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
