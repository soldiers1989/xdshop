package com.pujjr.gps.vo;

public class GpsBranchVo {
	private String branchId;
	private String branchName;
	private String enabled;

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

	/**
	 * @return enabled
	 */
	public String getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            要设置的 enabled
	 */
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

}
