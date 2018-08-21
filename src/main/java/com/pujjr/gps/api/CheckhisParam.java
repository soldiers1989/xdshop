package com.pujjr.gps.api;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class CheckhisParam {

	/* 申请单参数 */
	@ApiModelProperty(value = "申请单订单号")
	private String pubId;

	@ApiModelProperty(value = "有线gps数")
	private Integer wireNum;

	@ApiModelProperty(value = "无线gps数")
	private Integer wirelessNum;

	@ApiModelProperty(value = "审核审批状态")
	private String pubStatus;

	@ApiModelProperty(value = "意见")
	private String comment;

	@ApiModelProperty(value = "创建用户")
	private String createAccountId;

	@ApiModelProperty(value = "订单明细")
	private List<OrderDetailParam> orderDetailParamList;

	/**
	 * @return pubId
	 */
	public String getPubId() {
		return pubId;
	}

	/**
	 * @param pubId
	 *            要设置的 pubId
	 */
	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	/**
	 * @return wireNum
	 */
	public Integer getWireNum() {
		return wireNum;
	}

	/**
	 * @param wireNum
	 *            要设置的 wireNum
	 */
	public void setWireNum(Integer wireNum) {
		this.wireNum = wireNum;
	}

	/**
	 * @return wirelessNum
	 */
	public Integer getWirelessNum() {
		return wirelessNum;
	}

	/**
	 * @param wirelessNum
	 *            要设置的 wirelessNum
	 */
	public void setWirelessNum(Integer wirelessNum) {
		this.wirelessNum = wirelessNum;
	}

	/**
	 * @return pubStatus
	 */
	public String getPubStatus() {
		return pubStatus;
	}

	/**
	 * @param pubStatus
	 *            要设置的 pubStatus
	 */
	public void setPubStatus(String pubStatus) {
		this.pubStatus = pubStatus;
	}

	/**
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            要设置的 comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return createAccountId
	 */
	public String getCreateAccountId() {
		return createAccountId;
	}

	/**
	 * @param createAccountId
	 *            要设置的 createAccountId
	 */
	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	/**
	 * @return orderDetailParamList
	 */
	public List<OrderDetailParam> getOrderDetailParamList() {
		return orderDetailParamList;
	}

	/**
	 * @param orderDetailParamList
	 *            要设置的 orderDetailParamList
	 */
	public void setOrderDetailParamList(List<OrderDetailParam> orderDetailParamList) {
		this.orderDetailParamList = orderDetailParamList;
	}

}
