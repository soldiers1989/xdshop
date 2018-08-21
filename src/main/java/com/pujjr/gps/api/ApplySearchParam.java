package com.pujjr.gps.api;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class ApplySearchParam extends BaseParam{

	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;
	
	
	
	@ApiModelProperty(value = "申请单状态")
	private String applyStatus;

	@ApiModelProperty(value = "申请单号")
	private String applyId;

	@ApiModelProperty(value = "经销商编码")
	private String branchId;

	
	@ApiModelProperty(value = "申请时间")
	private Date applyTime;

	@ApiModelProperty(value = "申请单渠道")
	private String applyChannel;

	@ApiModelProperty(value = "页码")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "每页显示数")
	private int pageSize = PAGE_SIZE;
	
	@ApiModelProperty(value = "起始时间")
	private Date timeBegin;
	
	@ApiModelProperty(value = "截止时间")
	private Date timeEnd;
	
	
	
	public String getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * @return applyId
	 */
	public String getApplyId() {
		return applyId;
	}

	/**
	 * @param applyId
	 *            要设置的 applyId
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

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
	 * @return applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime
	 *            要设置的 applyTime
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	/**
	 * @return applyChannel
	 */
	public String getApplyChannel() {
		return applyChannel;
	}

	/**
	 * @param applyChannel
	 *            要设置的 applyChannel
	 */
	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
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

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

}
