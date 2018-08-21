package com.pujjr.gps.dal.domain;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gps_brand")
public class GpsBrand {
	@Id
	private String brandId;

	private String brandName;

	private String applyChannel;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getApplyChannel() {
		return applyChannel;
	}

	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}
}