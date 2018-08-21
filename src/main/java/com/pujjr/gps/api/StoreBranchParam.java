package com.pujjr.gps.api;

import io.swagger.annotations.ApiModelProperty;

/**
 * 经销商库存查询参数接收对象
 * 
 * @author tom
 *
 */
public class StoreBranchParam {
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;

	@ApiModelProperty(value = "GPS编号")
	private String gpsId;

	@ApiModelProperty(value = "经销商编号")
	private String branchId;

	@ApiModelProperty(value = "供应商编号")
	private String supplierId;

	@ApiModelProperty(value = "GPS种类")
	private String gpsCategory;

	@ApiModelProperty(value = "当前页")
	private int pageNum = PAGE_NUM;

	@ApiModelProperty(value = "页码大小")
	private int pageSize = PAGE_SIZE;

	@ApiModelProperty(value = "品牌编号")
	private String brandId;
	
	
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

	/**
	 * @return branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            要设置的 branchId
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId
	 *            要设置的 supplierId
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return gpsCategory
	 */
	public String getGpsCategory() {
		return gpsCategory;
	}

	/**
	 * @param gpsCategory
	 *            要设置的 gpsCategory
	 */
	public void setGpsCategory(String gpsCategory) {
		this.gpsCategory = gpsCategory;
	}

	/**
	 * @return gpsId
	 */
	public String getGpsId() {
		return gpsId;
	}

	/**
	 * @param gpsId
	 *            要设置的 gpsId
	 */
	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	

}
