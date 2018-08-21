package com.pujjr.gps.vo;

import java.util.List;

/**
 * @author wen
 * @date 创建时间：2017年11月24日 上午10:45:58
 *
 */
public class SendFeedbackVO {

	private String orderId;

	private String supplyName;

	private List<SendGpsDetailVO> sendGpsDetailList;

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
	 * @return supplyName
	 */
	public String getSupplyName() {
		return supplyName;
	}

	/**
	 * @param supplyName
	 *            要设置的 supplyName
	 */
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	/**
	 * @return sendGpsDetailList
	 */
	public List<SendGpsDetailVO> getSendGpsDetailList() {
		return sendGpsDetailList;
	}

	/**
	 * @param sendGpsDetailList
	 *            要设置的 sendGpsDetailList
	 */
	public void setSendGpsDetailList(List<SendGpsDetailVO> sendGpsDetailList) {
		this.sendGpsDetailList = sendGpsDetailList;
	}

}
