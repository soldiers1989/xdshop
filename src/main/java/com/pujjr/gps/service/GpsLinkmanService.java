package com.pujjr.gps.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.GpsLinkmanParam;
import com.pujjr.gps.dal.domain.GpsLinkman;

public interface GpsLinkmanService {
	
	public PageInfo<GpsLinkman> getGpsLinkmanListByParam(GpsLinkmanParam gpsLinkmanParam);
	
	public List<GpsLinkman> getGpsLinkmanListByBranchId(String branchId);
	
	public GpsLinkman saveGpsLinkman(GpsLinkman gpsLinkman);
	
	public GpsLinkman setDefault(GpsLinkmanParam gpsLinkmanParam);
	
	/**
	 * 新增
	 * @param gpsLinkman
	 * @return
	 */
	public GpsLinkman addGpsLinkman(GpsLinkman gpsLinkman);
	
	/**
	 * 修改
	 * @param gpsLinkman
	 * @return
	 */
	public GpsLinkman updateGpsLinkman(GpsLinkman gpsLinkman);
	
	/**
	 * 删除
	 * @param gpsLinkman
	 * @return
	 */
	public GpsLinkman deleteGpsLinkman(GpsLinkman gpsLinkman);
	
	public GpsLinkman getDefaultGpsLinkman(String branchId);
	

	
}
