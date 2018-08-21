package com.pujjr.gps.vo;

import java.util.List;

import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsSupplier;

public class GpsOrderVo extends GpsOrder{
	/**
	 * GPS供应商对象
	 */
	private GpsSupplier gpsSupplier;
	/**
	 * 订单相关订单明细
	 */
	private List<GpsOrderDetailVo> gpsOrderDetails;
	public GpsSupplier getGpsSupplier() {
		return gpsSupplier;
	}
	public void setGpsSupplier(GpsSupplier gpsSupplier) {
		this.gpsSupplier = gpsSupplier;
	}
	public List<GpsOrderDetailVo> getGpsOrderDetails() {
		return gpsOrderDetails;
	}
	public void setGpsOrderDetails(List<GpsOrderDetailVo> gpsOrderDetails) {
		this.gpsOrderDetails = gpsOrderDetails;
	}
	
	
}
