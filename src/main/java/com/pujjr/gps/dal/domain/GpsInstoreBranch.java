package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_instore_branch")
public class GpsInstoreBranch {

	@Id
	private String instoreId;

	private String gpsId;

	private String branchId;

	private String branchName;

	private String brandId;

	private String gpsCategory;

	private String createAccountId;

	private Date createTime;

	public String getInstoreId() {
		return instoreId;
	}

	public void setInstoreId(String instoreId) {
		this.instoreId = instoreId;
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

	public String getCreateAccountId() {
		return createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}