package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_gps_store_branch")
public class GpsStoreBranch {
	@Id
	@ApiModelProperty(value = "经销商库存编号")
	private String storeId;

	@ApiModelProperty(value = "GPS编号")
	private String gpsId;

	@ApiModelProperty(value = "经销商编号")
	private String branchId;

	@ApiModelProperty(value = "经销商名称")
	private String branchName;

	@ApiModelProperty(value = "品牌编号")
	private String brandId;

	@ApiModelProperty(value = "GPS类型(WIRE:有线,WIRELESS:无线)")
	private String gpsCategory;

	@ApiModelProperty(value = "GPS状态(200:空闲,201:锁定,202:已安装,203:禁用)")
	private String gpsStatus;

	@ApiModelProperty(value = "供应商编号")
	private String supplierId;

	@ApiModelProperty(value = "库存编号")
	private String storeNo;

	@ApiModelProperty(value = "渠道")
	private String applyChannel;

	@ApiModelProperty(value = "申请单编号")
	private String applyId;

	@ApiModelProperty(value = "库存记录操作时间")
	private Date operTime;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getGpsCategory() {
		return gpsCategory;
	}

	public void setGpsCategory(String gpsCategory) {
		this.gpsCategory = gpsCategory;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getApplyChannel() {
		return applyChannel;
	}

	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

}