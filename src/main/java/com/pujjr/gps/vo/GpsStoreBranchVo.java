package com.pujjr.gps.vo;

import com.pujjr.gps.dal.domain.GpsStoreBranch;

import io.swagger.annotations.ApiModelProperty;

/**
 * 潽金库存vo对象
 * @author tom
 *
 */
public class GpsStoreBranchVo  extends GpsStoreBranch{
	private String brandName;
	private String gpsCategoryName;
	private String gpsStatusName;
	private String supplierName;
	
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;
	@ApiModelProperty(value = "当前页")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "页码大小")
	private int pageSize = PAGE_SIZE;
	
	
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getGpsCategoryName() {
		return gpsCategoryName;
	}
	public void setGpsCategoryName(String gpsCategoryName) {
		this.gpsCategoryName = gpsCategoryName;
	}
	public String getGpsStatusName() {
		return gpsStatusName;
	}
	public void setGpsStatusName(String gpsStatusName) {
		this.gpsStatusName = gpsStatusName;
	}
}
