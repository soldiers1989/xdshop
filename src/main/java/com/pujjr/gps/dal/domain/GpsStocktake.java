package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Table;

@Table(name = "t_gps_stocktake")
public class GpsStocktake {
    private String stocktakeId;

	private Integer usedWireNum;

	private Integer usedWirelessNum;

	private Integer totalWireNum;

	private Integer totalWirelessNum;

	private Integer applyWireNum;

	private Integer applyWirelessNum;

	private String branchId;

	private String branchName;

	private Date createTime;
	
	private String stocktakeType;

	public String getStocktakeId() {
		return stocktakeId;
	}

	public void setStocktakeId(String stocktakeId) {
		this.stocktakeId = stocktakeId;
	}

	public Integer getUsedWireNum() {
		return usedWireNum;
	}

	public void setUsedWireNum(Integer usedWireNum) {
		this.usedWireNum = usedWireNum;
	}

	public Integer getUsedWirelessNum() {
		return usedWirelessNum;
	}

	public void setUsedWirelessNum(Integer usedWirelessNum) {
		this.usedWirelessNum = usedWirelessNum;
	}

	public Integer getTotalWireNum() {
		return totalWireNum;
	}

	public void setTotalWireNum(Integer totalWireNum) {
		this.totalWireNum = totalWireNum;
	}

	public Integer getTotalWirelessNum() {
		return totalWirelessNum;
	}

	public void setTotalWirelessNum(Integer totalWirelessNum) {
		this.totalWirelessNum = totalWirelessNum;
	}

	public Integer getApplyWireNum() {
		return applyWireNum;
	}

	public void setApplyWireNum(Integer applyWireNum) {
		this.applyWireNum = applyWireNum;
	}

	public Integer getApplyWirelessNum() {
		return applyWirelessNum;
	}

	public void setApplyWirelessNum(Integer applyWirelessNum) {
		this.applyWirelessNum = applyWirelessNum;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStocktakeType() {
		return stocktakeType;
	}

	public void setStocktakeType(String stocktakeType) {
		this.stocktakeType = stocktakeType;
	}
	
	

}