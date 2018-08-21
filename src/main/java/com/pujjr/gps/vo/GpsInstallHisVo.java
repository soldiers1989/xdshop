package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsInstoreHis;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.dal.domain.GpsStoreBranch;

/**
 * 经销商入库历史
 * @author tom
 *
 */
public class GpsInstallHisVo extends GpsInstoreHis{
	/**
	 * 经销商库存记录
	 */
	private GpsStoreBranch gpsStoreBranch;
	/**
	 * 收货表记录
	 */
	private GpsReceive gpsReceive;
	public GpsStoreBranch getGpsStoreBranch() {
		return gpsStoreBranch;
	}
	public void setGpsStoreBranch(GpsStoreBranch gpsStoreBranch) {
		this.gpsStoreBranch = gpsStoreBranch;
	}
	public GpsReceive getGpsReceive() {
		return gpsReceive;
	}
	public void setGpsReceive(GpsReceive gpsReceive) {
		this.gpsReceive = gpsReceive;
	}
	
	
}
