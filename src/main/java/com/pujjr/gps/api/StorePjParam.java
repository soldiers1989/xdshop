package com.pujjr.gps.api;

import com.pujjr.gps.dal.domain.GpsStorePj;
/**
 * 潽金库存查询参数接收对象
 * @author tom
 *
 */
public class StorePjParam extends GpsStorePj{
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
	
	

}
