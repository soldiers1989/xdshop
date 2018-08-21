package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.gps.api.GpsLinkmanParam;
import com.pujjr.gps.dal.domain.GpsLinkman;

public interface GpsLinkmanMapper{

	int deleteByPrimaryKey(String linkmanId);

	int insert(GpsLinkman record);

	int insertSelective(GpsLinkman record);

	GpsLinkman selectByPrimaryKey(String linkmanId);

	int updateByPrimaryKeySelective(GpsLinkman record);

	int updateByPrimaryKey(GpsLinkman record);

	
	List<GpsLinkman> selectGpsLinkmanByParam(@Param("gpsLinkmanParam") GpsLinkmanParam gpsLinkmanParam);
	
	List<GpsLinkman> selectGpsLinkmanByBranchId(String branchId);
	
	
	GpsLinkman selectDefaultLinkmanByBranchId(String branchId);
	
	List<GpsLinkman> selectAll();
	
	

}