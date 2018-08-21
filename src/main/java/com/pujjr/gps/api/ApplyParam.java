package com.pujjr.gps.api;

import com.pujjr.gps.dal.domain.GpsStocktake;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class ApplyParam {

	/* 申请单参数 */
	@ApiModelProperty(value = "申请单号")
	private String applyId;

	@ApiModelProperty(value = "盘点编号")
	private String stocktakeId;

	@ApiModelProperty(value = "经销商编码")
	private String branchId;

	@ApiModelProperty(value = "经销商名称")
	private String branchName;

	@ApiModelProperty(value = "申请单渠道")
	private String applyChannel;

	@ApiModelProperty(value = "有线申请数量")
	private Integer wireNum = 0;

	@ApiModelProperty(value = "无线申请数量")
	private Integer wirelessNum = 0;

	@ApiModelProperty(value = "创建人")
	private String createAccountId;

	@ApiModelProperty(value = "申请单类型")
	private String applyType;

	@ApiModelProperty(value = "备注")
	private String remark;

	/* 经销商联系人参数 */

	@ApiModelProperty(value = "联系人编号")
	private String linkmanId;
	
	private GpsStocktake gpsStocktake;

	
	/**
	 * @return branchCode
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchCode
	 *            要设置的 branchCode
	 */
	public void setBranchId(String branchCode) {
		this.branchId = branchCode;
	}

	/**
	 * @return branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            要设置的 branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return applyChannel
	 */
	public String getApplyChannel() {
		return applyChannel;
	}

	/**
	 * @param applyChannel
	 *            要设置的 applyChannel
	 */
	public void setApplyChannel(String applyChannel) {
		this.applyChannel = applyChannel;
	}

	/**
	 * @return wireNum
	 */
	public Integer getWireNum() {
		return wireNum;
	}

	/**
	 * @param wireNum
	 *            要设置的 wireNum
	 */
	public void setWireNum(Integer wireNum) {
		this.wireNum = wireNum;
	}

	/**
	 * @return wirelessNum
	 */
	public Integer getWirelessNum() {
		return wirelessNum;
	}

	/**
	 * @param wirelessNum
	 *            要设置的 wirelessNum
	 */
	public void setWirelessNum(Integer wirelessNum) {
		this.wirelessNum = wirelessNum;
	}

	/**
	 * @return createAccountId
	 */
	public String getCreateAccountId() {
		return createAccountId;
	}

	/**
	 * @param createAccountId
	 *            要设置的 createAccountId
	 */
	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	/**
	 * @return applyType
	 */
	public String getApplyType() {
		return applyType;
	}

	/**
	 * @param applyType
	 *            要设置的 applyType
	 */
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	/**
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            要设置的 remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	 * @return linkmanId
	 */
	public String getLinkmanId() {
		return linkmanId;
	}

	/**
	 * @param linkmanId
	 *            要设置的 linkmanId
	 */
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	/**
	 * @return stocktakeId
	 */
	public String getStocktakeId() {
		return stocktakeId;
	}

	/**
	 * @param stocktakeId
	 *            要设置的 stocktakeId
	 */
	public void setStocktakeId(String stocktakeId) {
		this.stocktakeId = stocktakeId;
	}

	public GpsStocktake getGpsStocktake() {
		return gpsStocktake;
	}

	public void setGpsStocktake(GpsStocktake gpsStocktake) {
		this.gpsStocktake = gpsStocktake;
	}

	
}
