package com.pujjr.gps.dal.dao;

import java.util.List;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsApplyback;

public interface GpsApplybackMapper extends SuperMapper<GpsApplyback> {
	List<GpsApplyback> selectNotFinishApplyback();
}