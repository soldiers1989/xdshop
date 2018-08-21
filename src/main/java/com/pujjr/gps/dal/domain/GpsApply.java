package com.pujjr.gps.dal.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author wen
 *
 */
@Table(name = "t_gps_apply")
public class GpsApply {

	public final static int REMARK_LENGTH = 512;

	@Id
	private String applyId;

	private String branchId;

	private String branchName;

	private String stocktakeId;

	private Date applyTime;

	private String applyChannel;

	private String applyStatus;

	private String linkmanId;

	private Integer wireNum = 0;

	private Integer wirelessNum = 0;

	private String createAccountId;

	private String applyType;

	private Integer waitReceiveOrderNum = 0;

	private String remark;
	
	private Integer freeWireNum;
	private Integer freeWirelessNum;
	private Integer usedWireNum;
	private Integer usedWirelessNum;
	private Integer totalWireNum;
	private Integer totalWirelessNum;
	@Transient
	private Integer unusedWireNum = 0;

	@Transient
	private Integer unusedWirelessNum = 0;

	@Transient
	private GpsLinkman gpsLinkman;

	@Transient
	private List<GpsCheckhis> gpsCheckhisList;

	@Transient
	private List<GpsOrderDetail> gpsOrderDetailList;
	
	@Transient
	private GpsStocktake gpsStocktake;
	
	public String getStocktakeId() {
		return stocktakeId;
	}

	public void setStocktakeId(String stocktakeId) {
		this.stocktakeId = stocktakeId;
	}

	

	/**
	 * @return waitReceiveOrderNum
	 */
	public Integer getWaitReceiveOrderNum() {
		return waitReceiveOrderNum;
	}

	/**
	 * @param waitReceiveOrderNum
	 *            要设置的 waitReceiveOrderNum
	 */
	public void setWaitReceiveOrderNum(Integer waitReceiveOrderNum) {
		this.waitReceiveOrderNum = waitReceiveOrderNum;
	}

	/**
	 * @return gpsCheckhisList
	 */
	public List<GpsCheckhis> getGpsCheckhisList() {
		return gpsCheckhisList;
	}

	/**
	 * @param gpsCheckhisList
	 *            要设置的 gpsCheckhisList
	 */
	public void setGpsCheckhisList(List<GpsCheckhis> gpsCheckhisList) {
		this.gpsCheckhisList = gpsCheckhisList;
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

	
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
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
	 * @return applyTime
	 */
	public Date getApplyTime() {
		return applyTime;
	}

	/**
	 * @param applyTime
	 *            要设置的 applyTime
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
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
	 * @return applyStatus
	 */
	public String getApplyStatus() {
		return applyStatus;
	}

	/**
	 * @param applyStatus
	 *            要设置的 applyStatus
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
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
	 * @return gpsLinkman
	 */
	public GpsLinkman getGpsLinkman() {
		return gpsLinkman;
	}

	/**
	 * @param gpsLinkman
	 *            要设置的 gpsLinkman
	 */
	public void setGpsLinkman(GpsLinkman gpsLinkman) {
		this.gpsLinkman = gpsLinkman;
	}

	/**
	 * @return unusedWireNum
	 */
	public Integer getUnusedWireNum() {
		return unusedWireNum;
	}

	/**
	 * @param unusedWireNum
	 *            要设置的 unusedWireNum
	 */
	public void setUnusedWireNum(Integer unusedWireNum) {
		this.unusedWireNum = unusedWireNum;
	}

	/**
	 * @return unusedWirelessNum
	 */
	public Integer getUnusedWirelessNum() {
		return unusedWirelessNum;
	}

	/**
	 * @param unusedWirelessNum
	 *            要设置的 unusedWirelessNum
	 */
	public void setUnusedWirelessNum(Integer unusedWirelessNum) {
		this.unusedWirelessNum = unusedWirelessNum;
	}

	/**
	 * @return gpsOrderDetailList
	 */
	public List<GpsOrderDetail> getGpsOrderDetailList() {
		return gpsOrderDetailList;
	}

	/**
	 * @param gpsOrderDetailList
	 *            要设置的 gpsOrderDetailList
	 */
	public void setGpsOrderDetailList(List<GpsOrderDetail> gpsOrderDetailList) {
		this.gpsOrderDetailList = gpsOrderDetailList;
	}

	public Integer getFreeWireNum() {
		return freeWireNum;
	}

	public void setFreeWireNum(Integer freeWireNum) {
		this.freeWireNum = freeWireNum;
	}

	public Integer getFreeWirelessNum() {
		return freeWirelessNum;
	}

	public void setFreeWirelessNum(Integer freeWirelessNum) {
		this.freeWirelessNum = freeWirelessNum;
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

	public GpsStocktake getGpsStocktake() {
		return gpsStocktake;
	}

	public void setGpsStocktake(GpsStocktake gpsStocktake) {
		this.gpsStocktake = gpsStocktake;
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
	
	

}