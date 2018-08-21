package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsApplybackDetail;

/**
 * 退货gps vo层对象
 * @author tom
 *
 */
//public class ApplyBackGpsVo extends GpsStoreBranch{
public class ApplyBackGpsVo extends GpsApplybackDetail{
	private String brandName;
	private String supplierName;
	private String gpsCategoryName;
	private String gpsStatusName;
	private String branchId;
	private String branchName;
	private String applybackDetailStatusName;
	
	

	public String getApplybackDetailStatusName() {
		return applybackDetailStatusName;
	}

	public void setApplybackDetailStatusName(String applybackDetailStatusName) {
		this.applybackDetailStatusName = applybackDetailStatusName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}
