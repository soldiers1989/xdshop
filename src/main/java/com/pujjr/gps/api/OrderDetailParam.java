package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class OrderDetailParam {

	@ApiModelProperty(value = "申请单号")
	private String applyId;

	@ApiModelProperty(value = "有线申请数量")
	private Integer wireNum = 0;

	@ApiModelProperty(value = "无线申请数量")
	private Integer wirelessNum = 0;

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

}
