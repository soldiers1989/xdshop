package com.pujjr.gps.dal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_gps_order")
public class GpsOrder {
	@Id
	private String orderId;

	private String supplierId;

	private Date orderTime;

	private Integer wireNum;

	private Integer wirelessNum;

	private Integer totalNum;

	private String createAccountId;

	private Date createTime;

	private String orderStatus;

	@Transient
	private GpsSupplier gpsSupplier;

	@Transient
	private List<GpsOrderDetail> gpsOrderDetailList;

	@Transient
	private List<GpsCheckhis> gpsCheckhisList;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * @return gpsOrderDetailList
	 */
	public List<GpsOrderDetail> getGpsOrderDetailList() {
		return gpsOrderDetailList;
	}

	/**
	 * @param gpsOrderDetailList
	 *            要设置的 gpsOrderDetailList
	 */
	public void setGpsOrderDetailList(List<GpsOrderDetail> gpsOrderDetailList) {
		this.gpsOrderDetailList = gpsOrderDetailList;
	}

	/**
	 * @return gpsSupplier
	 */
	public GpsSupplier getGpsSupplier() {
		return gpsSupplier;
	}

	/**
	 * @param gpsSupplier
	 *            要设置的 gpsSupplier
	 */
	public void setGpsSupplier(GpsSupplier gpsSupplier) {
		this.gpsSupplier = gpsSupplier;
	}

	/**
	 * @return gpsCheckhisList
	 */
	public List<GpsCheckhis> getGpsCheckhisList() {
		return gpsCheckhisList;
	}

	/**
	 * @param gpsCheckhisList
	 *            要设置的 gpsCheckhisList
	 */
	public void setGpsCheckhisList(List<GpsCheckhis> gpsCheckhisList) {
		this.gpsCheckhisList = gpsCheckhisList;
	}

}