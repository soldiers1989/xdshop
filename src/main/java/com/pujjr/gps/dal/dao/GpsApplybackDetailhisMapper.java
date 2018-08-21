package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsApplybackDetailhis;

public interface GpsApplybackDetailhisMapper extends SuperMapper<GpsApplybackDetailhis> {

	/**
	 * 查询指定退货申请单详情
	 * 
	 * @param applybackId
	 * @return 申请单详情
	 */
	@Select("select * from t_gps_applyback_detailhis where applyback_id = #{applybackId}")
	List<GpsApplybackDetailhis> selectApplybackDetailhisByApplybackId(String applybackId);
}