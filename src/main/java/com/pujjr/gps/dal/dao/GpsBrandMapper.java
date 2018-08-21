package com.pujjr.gps.dal.dao;

import java.util.List;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsBrand;

public interface GpsBrandMapper extends SuperMapper<GpsBrand> {

	int deleteByPrimaryKey(String brandId);

	int insert(GpsBrand record);

	int insertSelective(GpsBrand record);

	GpsBrand selectByPrimaryKey(String brandId);

	int updateByPrimaryKeySelective(GpsBrand record);

	int updateByPrimaryKey(GpsBrand record);
	
	List<GpsBrand> selectAllGpsBrand();

}