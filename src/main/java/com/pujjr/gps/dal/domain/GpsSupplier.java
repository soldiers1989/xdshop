package com.pujjr.gps.dal.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Table(name = "t_gps_supplier")
public class GpsSupplier {

	/** 记录入库 */
	public static final String OUTSTORE_RECORD_IN = "0";

	/** 记录出库 */
	public static final String OUTSTORE_RECORD_OUT = "1";

	@Id
	@ApiModelProperty(value = "供应商编号")
	private String supplierId;

	@ApiModelProperty(value = "供应商名称")
	private String supplierName;

	@ApiModelProperty(value = "是否记录出入库(0:记录入库,1:记录出库)")
	private String isOutstoreRecord;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplyId) {
		this.supplierId = supplyId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	/**
	 * @return isOutstoreRecord
	 */
	public String getIsOutstoreRecord() {
		return isOutstoreRecord;
	}

	/**
	 * @param isOutstoreRecord
	 *            要设置的 isOutstoreRecord
	 */
	public void setIsOutstoreRecord(String isOutstoreRecord) {
		this.isOutstoreRecord = isOutstoreRecord;
	}

}