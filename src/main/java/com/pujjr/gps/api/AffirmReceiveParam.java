package com.pujjr.gps.api;

import java.util.List;

import com.pujjr.gps.dal.domain.GpsReceive;

/**
 * 确认收货参数接收
 * @author tom
 *
 */
public class AffirmReceiveParam {
	//收货人
	private String accountId;
	//确认收货gps
	List<GpsReceive> gpsReceiveList;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<GpsReceive> getGpsReceiveList() {
		return gpsReceiveList;
	}
	public void setGpsReceiveList(List<GpsReceive> gpsReceiveList) {
		this.gpsReceiveList = gpsReceiveList;
	}
	
	
}
