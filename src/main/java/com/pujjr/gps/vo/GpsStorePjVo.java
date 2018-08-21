package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsStorePj;

/**
 * 潽金库存vo对象
 * @author tom
 *
 */
public class GpsStorePjVo  extends GpsStorePj{
	private String brandName;
	private String gpsCategoryName;
	private String gpsStatusName;
	
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getGpsCategoryName() {
		return gpsCategoryName;
	}
	public void setGpsCategoryName(String gpsCategoryName) {
		this.gpsCategoryName = gpsCategoryName;
	}
	public String getGpsStatusName() {
		return gpsStatusName;
	}
	public void setGpsStatusName(String gpsStatusName) {
		this.gpsStatusName = gpsStatusName;
	}
	
	
	
}
