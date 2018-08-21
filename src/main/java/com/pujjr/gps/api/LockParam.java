package com.pujjr.gps.api;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class LockParam {

	@ApiModelProperty(value = "要解锁的gps")
	private List<String> unlockGpsIdList;

	@ApiModelProperty(value = "要锁定的gps")
	private List<String> lockGpsIdList;

	@ApiModelProperty(value = "要出货的gps")
	private List<String> outStoreGpsIdList;

	@ApiModelProperty(value = "要恢复的gps")
	private List<String> recoverGpsList;

	@ApiModelProperty(value = "申请单号")
	private String appId;

	/**
	 * @return unlockGpsIdList
	 */
	public List<String> getUnlockGpsIdList() {
		return unlockGpsIdList;
	}

	/**
	 * @param unlockGpsIdList
	 *            要设置的 unlockGpsIdList
	 */
	public void setUnlockGpsIdList(List<String> unlockGpsIdList) {
		this.unlockGpsIdList = unlockGpsIdList;
	}

	/**
	 * @return lockGpsIdList
	 */
	public List<String> getLockGpsIdList() {
		return lockGpsIdList;
	}

	/**
	 * @param lockGpsIdList
	 *            要设置的 lockGpsIdList
	 */
	public void setLockGpsIdList(List<String> lockGpsIdList) {
		this.lockGpsIdList = lockGpsIdList;
	}

	/**
	 * @return outStoreGpsIdList
	 */
	public List<String> getOutStoreGpsIdList() {
		return outStoreGpsIdList;
	}

	/**
	 * @param outStoreGpsIdList
	 *            要设置的 outStoreGpsIdList
	 */
	public void setOutStoreGpsIdList(List<String> outStoreGpsIdList) {
		this.outStoreGpsIdList = outStoreGpsIdList;
	}

	/**
	 * @return recoverGpsList
	 */
	public List<String> getRecoverGpsList() {
		return recoverGpsList;
	}

	/**
	 * @param recoverGpsList
	 *            要设置的 recoverGpsList
	 */
	public void setRecoverGpsList(List<String> recoverGpsList) {
		this.recoverGpsList = recoverGpsList;
	}

	/**
	 * @return appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId
	 *            要设置的 appId
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

}
