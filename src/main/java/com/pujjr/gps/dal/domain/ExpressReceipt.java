package com.pujjr.gps.dal.domain;

import java.util.List;

/**
 * 快递单对象
 * @author tom
 *
 */
public class ExpressReceipt {
	private String expressNo;
	private String expressCompany;
	private Integer wireNum;
	private Integer wirelessNum;
	private Integer totalNum;
	private String supplierId;
	private String supplierName;
	//快递单签收状态
	private String expressReceiptStatus;
	
	public String getExpressReceiptStatus() {
		return expressReceiptStatus;
	}
	public void setExpressReceiptStatus(String expressReceiptStatus) {
		this.expressReceiptStatus = expressReceiptStatus;
	}
	/**
	 * 快递单gps列表
	 */
	private List<GpsReceive> gpsReceiveList;
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public List<GpsReceive> getGpsReceiveList() {
		return gpsReceiveList;
	}
	public void setGpsReceiveList(List<GpsReceive> gpsReceiveList) {
		this.gpsReceiveList = gpsReceiveList;
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
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
}
