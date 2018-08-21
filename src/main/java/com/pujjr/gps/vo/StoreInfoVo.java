package com.pujjr.gps.vo;
/**
 * 库存情况
 * @author tom
 *
 */
public class StoreInfoVo {
	//空闲总量
	private int totalFree;
	//安装总量
	private int totalUse;
	//锁定总量
	private int totalLock;
	//禁用总量
	private int totalForbidden;
	public int getTotalFree() {
		return totalFree;
	}
	public void setTotalFree(int totalFree) {
		this.totalFree = totalFree;
	}
	public int getTotalUse() {
		return totalUse;
	}
	public void setTotalUse(int totalUse) {
		this.totalUse = totalUse;
	}
	public int getTotalLock() {
		return totalLock;
	}
	public void setTotalLock(int totalLock) {
		this.totalLock = totalLock;
	}
	public int getTotalForbidden() {
		return totalForbidden;
	}
	public void setTotalForbidden(int totalForbidden) {
		this.totalForbidden = totalForbidden;
	}
	
	
}
