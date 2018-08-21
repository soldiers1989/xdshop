package com.pujjr.gps.dal.dao;

import com.pujjr.gps.dal.domain.GpsInstoreHis;

public interface GpsInstoreHisMapper {
    int deleteByPrimaryKey(String instoreHisId);

    int insert(GpsInstoreHis record);

    int insertSelective(GpsInstoreHis record);

    GpsInstoreHis selectByPrimaryKey(String instoreHisId);

    int updateByPrimaryKeySelective(GpsInstoreHis record);

    int updateByPrimaryKey(GpsInstoreHis record);
}