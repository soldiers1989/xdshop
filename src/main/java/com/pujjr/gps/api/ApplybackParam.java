package com.pujjr.gps.api;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class ApplybackParam {

	@ApiModelProperty(value = "退回申请编号")
	private String applybackId;

	@ApiModelProperty(value = "经销商编码")
	private String branchId;

	@ApiModelProperty(value = "经销商名称")
	private String branchName;

	@ApiModelProperty(value = "创建用户")
	private String createAccountId;

	@ApiModelProperty(value = "备注")
	private String applybackNote;

	@ApiModelProperty(value = "快递公司")
	private String expressCompany;

	@ApiModelProperty(value = "快递单号")
	private String expressNo;

	@ApiModelProperty(value = "退回明细列表")
	private List<ApplybackDetailParam> applyBackDetailParamList;

	/**
	 * @return applybackId
	 */
	public String getApplybackId() {
		return applybackId;
	}

	/**
	 * @param applybackId
	 *            要设置的 applybackId
	 */
	public void setApplybackId(String applybackId) {
		this.applybackId = applybackId;
	}

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
	 * @return applybacknNote
	 */
	public String getApplybackNote() {
		return applybackNote;
	}

	/**
	 * @param applybacknNote
	 *            要设置的 applybacknNote
	 */
	public void setApplybacknNote(String applybackNote) {
		this.applybackNote = applybackNote;
	}

	/**
	 * @return applyBackDetailParamList
	 */
	public List<ApplybackDetailParam> getApplyBackDetailParamList() {
		return applyBackDetailParamList;
	}

	/**
	 * @param applyBackDetailParamList
	 *            要设置的 applyBackDetailParamList
	 */
	public void setApplyBackDetailParamList(List<ApplybackDetailParam> applyBackDetailParamList) {
		this.applyBackDetailParamList = applyBackDetailParamList;
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
	 * @param applybackNote
	 *            要设置的 applybackNote
	 */
	public void setApplybackNote(String applybackNote) {
		this.applybackNote = applybackNote;
	}

}
