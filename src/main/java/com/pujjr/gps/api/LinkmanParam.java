package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wen
 * @date 创建时间：2017年11月15日 上午11:26:39
 *
 */
public class LinkmanParam {

	/* 经销商联系人参数 */

	@ApiModelProperty(value = "联系人编号")
	private String linkmanId;

	@ApiModelProperty(value = "联系人名称")
	private String name;

	@ApiModelProperty(value = "联系电话1")
	private String mobile1;

	@ApiModelProperty(value = "联系电话2")
	private String mobile2;

	@ApiModelProperty(value = "省")
	private String addrProvince;

	@ApiModelProperty(value = "市")
	private String addrCity;

	@ApiModelProperty(value = "区")
	private String addrDistrict;

	@ApiModelProperty(value = "街道")
	private String addrDetail;

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
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return mobile1
	 */
	public String getMobile1() {
		return mobile1;
	}

	/**
	 * @param mobile1
	 *            要设置的 mobile1
	 */
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	/**
	 * @return mobile2
	 */
	public String getMobile2() {
		return mobile2;
	}

	/**
	 * @param mobile2
	 *            要设置的 mobile2
	 */
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	/**
	 * @return addrProvince
	 */
	public String getAddrProvince() {
		return addrProvince;
	}

	/**
	 * @param addrProvince
	 *            要设置的 addrProvince
	 */
	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	/**
	 * @return addrCity
	 */
	public String getAddrCity() {
		return addrCity;
	}

	/**
	 * @param addrCity
	 *            要设置的 addrCity
	 */
	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	/**
	 * @return addrDistrict
	 */
	public String getAddrDistrict() {
		return addrDistrict;
	}

	/**
	 * @param addrDistrict
	 *            要设置的 addrDistrict
	 */
	public void setAddrDistrict(String addrDistrict) {
		this.addrDistrict = addrDistrict;
	}

	/**
	 * @return addrDetail
	 */
	public String getAddrDetail() {
		return addrDetail;
	}

	/**
	 * @param addrDetail
	 *            要设置的 addrDetail
	 */
	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

}
