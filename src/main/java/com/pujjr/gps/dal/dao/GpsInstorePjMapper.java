package com.pujjr.gps.dal.dao;

import com.pujjr.gps.dal.domain.GpsInstorePj;

public interface GpsInstorePjMapper {
    int deleteByPrimaryKey(String instoreId);

	int insert(GpsInstorePj record);

	int insertSelective(GpsInstorePj record);

	GpsInstorePj selectByPrimaryKey(String instoreId);

	int updateByPrimaryKeySelective(GpsInstorePj record);

	int updateByPrimaryKey(GpsInstorePj record);

}