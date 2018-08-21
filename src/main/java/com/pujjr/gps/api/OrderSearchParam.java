package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class OrderSearchParam {

	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;

	@ApiModelProperty(value = "订单号")
	private String orderId;

	@ApiModelProperty(value = "经销商编号")
	private String supplyId;

	@ApiModelProperty(value = "采购日起")
	private String orderTime;

	@ApiModelProperty(value = "页码")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "每页显示数")
	private int pageSize = PAGE_SIZE;

	/**
	 * @return orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId
	 *            要设置的 orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return supplyId
	 */
	public String getSupplyId() {
		return supplyId;
	}

	/**
	 * @param supplyId
	 *            要设置的 supplyId
	 */
	public void setSupplyId(String supplyId) {
		this.supplyId = supplyId;
	}

	/**
	 * @return orderTime
	 */
	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            要设置的 orderTime
	 */
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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
