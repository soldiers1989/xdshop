package com.pujjr.gps.service;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.dal.domain.GpsStocktake;

public interface StocktakeService {
	public PageInfo<GpsStocktake> selectGpsStoreTake(StocktakeParam stocktakeParam);
}
