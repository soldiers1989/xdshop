package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsCheckhis;

public interface GpsCheckhisMapper extends SuperMapper<GpsCheckhis> {

	@Select("select * from t_gps_checkhis where pub_id = #{pubId}")
	public List<GpsCheckhis> selectByPubId(String pubId);

}