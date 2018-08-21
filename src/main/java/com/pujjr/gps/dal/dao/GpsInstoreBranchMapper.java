package com.pujjr.gps.dal.dao;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsInstoreBranch;

public interface GpsInstoreBranchMapper extends SuperMapper<GpsInstoreBranch> {

	int insert(GpsInstoreBranch record);

	int insertSelective(GpsInstoreBranch record);

	GpsInstoreBranch selectByPrimaryKey(String instoreId);

	int updateByPrimaryKeySelective(GpsInstoreBranch record);

	int updateByPrimaryKey(GpsInstoreBranch record);

}