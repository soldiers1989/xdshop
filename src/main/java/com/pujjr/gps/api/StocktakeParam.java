package com.pujjr.gps.api;


import com.pujjr.gps.dal.domain.GpsStocktake;
/**
 * 批量盘点查询参数接收对象
 * @author tom
 *
 */
public class StocktakeParam extends GpsStocktake{
	/** 默认页码 */
	public final static int PAGE_NUM = 1;

	/** 默认每页显示数 */
	public final static int PAGE_SIZE = 10;
	/**
	 * 当前页
	 */
	private int pageNum = PAGE_NUM;

	/**
	 * 页码大小
	 */
	private int pageSize = PAGE_SIZE;
	
	private String accountId;
	private String timeBegin;
	private String timeEnd;
	private String accountName;
	
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}


	
	

}
