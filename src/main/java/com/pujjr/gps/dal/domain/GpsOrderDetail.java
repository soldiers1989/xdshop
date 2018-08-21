package com.pujjr.gps.dal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_gps_order_detail")
public class GpsOrderDetail {

	@Id
	private String detailId;

	private String applyId;

	private String orderId;

	private Integer wireNum;

	private Integer wirelessNum;

	private String createAccountId;

	private Date createTime;

	private String branchId;

	private String branchName;

	private String orderDetailStatus;

	@Transient
	private int sumWireNum = 0;

	@Transient
	private int sumWirelessNum = 0;

	@Transient
	private GpsApply gpsApply;

	@Transient
	private List<GpsSend> gpsSendList;

	@Transient
	private List<GpsReceive> gpsReceiveList;

	/**
	 * @return orderDetailStatus
	 */
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}

	/**
	 * @param orderDetailStatus
	 *            要设置的 orderDetailStatus
	 */
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getWireNum() {
		return wireNum;
	}

	public void setWireNum(Integer wireNum) {
		this.wireNum = wireNum;
	}

	public Integer getWirelessNum() {
		return wirelessNum;
	}

	public void setWirelessNum(Integer wirelessNum) {
		this.wirelessNum = wirelessNum;
	}

	public String getCreateAccountId() {
		return createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            要设置的 branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return gpsApply
	 */
	public GpsApply getGpsApply() {
		return gpsApply;
	}

	/**
	 * @param gpsApply
	 *            要设置的 gpsApply
	 */
	public void setGpsApply(GpsApply gpsApply) {
		this.gpsApply = gpsApply;
	}

	/**
	 * @return gpsSendList
	 */
	public List<GpsSend> getGpsSendList() {
		return gpsSendList;
	}

	/**
	 * @param gpsSendList
	 *            要设置的 gpsSendList
	 */
	public void setGpsSendList(List<GpsSend> gpsSendList) {
		this.gpsSendList = gpsSendList;
	}

	/**
	 * @return gpsReceiveList
	 */
	public List<GpsReceive> getGpsReceiveList() {
		return gpsReceiveList;
	}

	/**
	 * @param gpsReceiveList
	 *            要设置的 gpsReceiveList
	 */
	public void setGpsReceiveList(List<GpsReceive> gpsReceiveList) {
		this.gpsReceiveList = gpsReceiveList;
	}

	/**
	 * @return sumWireNum
	 */
	public int getSumWireNum() {
		return sumWireNum;
	}

	/**
	 * @param sumWireNum
	 *            要设置的 sumWireNum
	 */
	public void setSumWireNum(int sumWireNum) {
		this.sumWireNum = sumWireNum;
	}

	/**
	 * @return sumWirelessNum
	 */
	public int getSumWirelessNum() {
		return sumWirelessNum;
	}

	/**
	 * @param sumWirelessNum
	 *            要设置的 sumWirelessNum
	 */
	public void setSumWirelessNum(int sumWirelessNum) {
		this.sumWirelessNum = sumWirelessNum;
	}

}