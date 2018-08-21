package com.pujjr.gps.vo;

import java.util.List;

import com.pujjr.gps.dal.domain.ExpressReceipt;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsOrderDetail;

public class GpsApplyVo extends GpsApply{
	private Integer unusedWireNum;
	private Integer unusedWirelessNum;
	/**
	 * 联系人
	 */
	private GpsLinkman gpsLinkman;
	/**
	 * 申请单相关订单明细
	 */
	private List<GpsOrderDetail> gpsOrderDetailList;
	/**
	 * 申请单相关审核审批记录	
	 */
	private List<GpsCheckhis> gpsCheckhisList;
	/**
	 * 待收货快递单
	 */
	private List<ExpressReceipt> expressReceiptList;
	
	public Integer getUnusedWireNum() {
		return unusedWireNum;
	}
	public void setUnusedWireNum(Integer unusedWireNum) {
		this.unusedWireNum = unusedWireNum;
	}
	public Integer getUnusedWirelessNum() {
		return unusedWirelessNum;
	}
	public void setUnusedWirelessNum(Integer unusedWirelessNum) {
		this.unusedWirelessNum = unusedWirelessNum;
	}
	public GpsLinkman getGpsLinkman() {
		return gpsLinkman;
	}
	public void setGpsLinkman(GpsLinkman gpsLinkman) {
		this.gpsLinkman = gpsLinkman;
	}
	public List<GpsOrderDetail> getGpsOrderDetailList() {
		return gpsOrderDetailList;
	}
	public void setGpsOrderDetailList(List<GpsOrderDetail> gpsOrderDetailList) {
		this.gpsOrderDetailList = gpsOrderDetailList;
	}
	public List<GpsCheckhis> getGpsCheckhisList() {
		return gpsCheckhisList;
	}
	public void setGpsCheckhisList(List<GpsCheckhis> gpsCheckhisList) {
		this.gpsCheckhisList = gpsCheckhisList;
	}
	public List<ExpressReceipt> getExpressReceiptList() {
		return expressReceiptList;
	}
	public void setExpressReceiptList(List<ExpressReceipt> expressReceiptList) {
		this.expressReceiptList = expressReceiptList;
	}
	
	
}
