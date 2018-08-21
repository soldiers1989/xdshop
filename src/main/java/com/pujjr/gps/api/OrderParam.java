package com.pujjr.gps.api;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class OrderParam {

	@ApiModelProperty(value = "订单号")
	private String orderId;

	@ApiModelProperty(value = "供应商号")
	private String supplierId;

	@ApiModelProperty(value = "创建用户")
	private String createAccountId;

	@ApiModelProperty(value = "订单明细")
	private List<OrderDetailParam> orderDetailParamList;

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
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            要设置的 supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
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
