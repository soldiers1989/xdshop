package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * 确认收货参数接收
 * 
 * @author tom
 *
 */
public class ReceiveParam {

	@ApiModelProperty(value = "收货清单编号")
	private String receiveId;

	@ApiModelProperty(value = "订单明细编号")
	private String detailId;

	@ApiModelProperty(value = "品牌编号")
	private String brandId;

	@ApiModelProperty(value = "GPS编号")
	private String gpsId;

	@ApiModelProperty(value = "GPS类型")
	private String gpsCategory;

	@ApiModelProperty(value = "快递公司")
	private String expressCompany;

	@ApiModelProperty(value = "快递单号")
	private String expressNo;

	@ApiModelProperty(value = "供应商编号")
	private String supplierId;

	@ApiModelProperty(value = "申请单编号")
	private String applyId;

	@ApiModelProperty(value = "操作用户")
	private String operUserId;

	
	/**
	 * @return receiveId
	 */
	public String getReceiveId() {
		return receiveId;
	}

	/**
	 * @param receiveId
	 *            要设置的 receiveId
	 */
	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}

	/**
	 * @return detailId
	 */
	public String getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId
	 *            要设置的 detailId
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return brandId
	 */
	public String getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId
	 *            要设置的 brandId
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return gpsId
	 */
	public String getGpsId() {
		return gpsId;
	}

	/**
	 * @param gpsId
	 *            要设置的 gpsId
	 */
	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	/**
	 * @return gpsCategory
	 */
	public String getGpsCategory() {
		return gpsCategory;
	}

	/**
	 * @param gpsCategory
	 *            要设置的 gpsCategory
	 */
	public void setGpsCategory(String gpsCategory) {
		this.gpsCategory = gpsCategory;
	}

	/**
	 * @return expressCompany
	 */
	public String getExpressCompany() {
		return expressCompany;
	}

	/**
	 * @param expressCompany
	 *            要设置的 expressCompany
	 */
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	/**
	 * @return expressNo
	 */
	public String getExpressNo() {
		return expressNo;
	}

	/**
	 * @param expressNo
	 *            要设置的 expressNo
	 */
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	/**
	 * @return supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            要设置的 supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return applyId
	 */
	public String getApplyId() {
		return applyId;
	}

	/**
	 * @param applyId
	 *            要设置的 applyId
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	/**
	 * @return operUserId
	 */
	public String getOperUserId() {
		return operUserId;
	}

	/**
	 * @param operUserId
	 *            要设置的 operUserId
	 */
	public void setOperUserId(String operUserId) {
		this.operUserId = operUserId;
	}


}
