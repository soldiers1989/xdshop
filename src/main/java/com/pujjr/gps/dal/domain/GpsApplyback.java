package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gps_applyback")
public class GpsApplyback {
	@Id
	private String applybackId;

	private String branchId;

	private String branchName;

	private String acreateCcountId;

	private Date applyTime;

	private String applybackStatus;

	private String applybackNote;

	private String expressCompany;

	private String expressNo;

	public String getApplybackId() {
		return applybackId;
	}

	public void setApplybackId(String applybackId) {
		this.applybackId = applybackId;
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

	public String getAcreateCcountId() {
		return acreateCcountId;
	}

	public void setAcreateCcountId(String acreateCcountId) {
		this.acreateCcountId = acreateCcountId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplybackStatus() {
		return applybackStatus;
	}

	public void setApplybackStatus(String applybackStatus) {
		this.applybackStatus = applybackStatus;
	}

	public String getApplybackNote() {
		return applybackNote;
	}

	public void setApplybackNote(String applybackNote) {
		this.applybackNote = applybackNote;
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

}