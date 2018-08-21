package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsInstorePj;
import com.pujjr.gps.dal.domain.GpsStorePj;

public class GpsInstallPjVo extends GpsInstorePj{
	/**
	 * 潽金库存记录
	 */
	private GpsStorePj gpsStorePj;

	public GpsStorePj getGpsStorePj() {
		return gpsStorePj;
	}

	public void setGpsStorePj(GpsStorePj gpsStorePj) {
		this.gpsStorePj = gpsStorePj;
	}
	
}
