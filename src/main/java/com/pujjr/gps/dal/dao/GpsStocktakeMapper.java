package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsStocktake;

public interface GpsStocktakeMapper extends SuperMapper<GpsStocktake> {

	/**
	 * 查询盘点信息
	 * @param branchId
	 * @param timeBegin
	 * @param timeEnd
	 * @return
	 */
	public List<GpsStocktake> selectGpsStoreTake(@Param("stocktakeParam") StocktakeParam stocktakeParam);
	public GpsStocktake selectGpsStocktakeById(String stocktakeId);
}