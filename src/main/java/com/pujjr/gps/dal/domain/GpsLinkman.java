package com.pujjr.gps.dal.domain;

import javax.persistence.Table;

@Table(name = "t_gps_linkman")
public class GpsLinkman {

	private String linkmanId;

	private String name;

	private String mobile1;

	private String mobile2;

	private String branchId;

	private String addrProvince;

	private String addrProvinceName;

	private String addrCity;

	private String addrCityName;

	private String addrDistrict;

	private String addrDistrictName;

	private String addrDetail;

	private String branchName;

	private String email;

	private Boolean isDefault;

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrProvinceName() {
		return addrProvinceName;
	}

	public void setAddrProvinceName(String addrProvinceName) {
		this.addrProvinceName = addrProvinceName;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrCityName() {
		return addrCityName;
	}

	public void setAddrCityName(String addrCityName) {
		this.addrCityName = addrCityName;
	}

	public String getAddrDistrict() {
		return addrDistrict;
	}

	public void setAddrDistrict(String addrDistrict) {
		this.addrDistrict = addrDistrict;
	}

	public String getAddrDistrictName() {
		return addrDistrictName;
	}

	public void setAddrDistrictName(String addrDistrictName) {
		this.addrDistrictName = addrDistrictName;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	

	public final static int NAME_LENGTH = 32;

	public final static int ADDR_PROVINCE_LENGTH = 16;

	public final static int ADDR_CITY_LENGTH = 32;

	public final static int ADDR_DISTRICT_LENGTH = 32;

	public final static int ADDR_DETAIL_LENGTH = 128;

}