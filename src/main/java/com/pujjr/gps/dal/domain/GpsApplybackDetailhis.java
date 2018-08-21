package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gps_applyback_detailhis")
public class GpsApplybackDetailhis {

	@Id
	private String applybackDetailId;

	private String brandId;

	private String applybackId;

	private String gpsId;

	private String supplierId;

	private String supplierName;

	private String gpsStatus;

	private String gpsCategory;

	private String applybackDetailStatus;

	private String createAccountId;

	private Date operTime;

	private String remark;

	public String getApplybackDetailId() {
		return applybackDetailId;
	}

	public void setApplybackDetailId(String applybackDetailId) {
		this.applybackDetailId = applybackDetailId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getApplybackId() {
		return applybackId;
	}

	public void setApplybackId(String applybackId) {
		this.applybackId = applybackId;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
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

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public String getGpsCategory() {
		return gpsCategory;
	}

	public void setGpsCategory(String gpsCategory) {
		this.gpsCategory = gpsCategory;
	}

	public String getApplybackDetailStatus() {
		return applybackDetailStatus;
	}

	public void setApplybackDetailStatus(String applybackDetailStatus) {
		this.applybackDetailStatus = applybackDetailStatus;
	}

	public String getCreateAccountId() {
		return createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}