package com.pujjr.gps.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.dal.dao.GpsStocktakeMapper;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.service.StocktakeService;
@Service
public class StocktakeServiceImpl implements StocktakeService{
	@Autowired
	private GpsStocktakeMapper gpsStocktakeMapper;
	@Override
	public PageInfo<GpsStocktake> selectGpsStoreTake(StocktakeParam stocktakeParam) {
		PageHelper.startPage(stocktakeParam.getPageNum(), stocktakeParam.getPageSize());
		List<GpsStocktake> gpsStocktakeList = gpsStocktakeMapper.selectGpsStoreTake(stocktakeParam);
		PageInfo<GpsStocktake> page = new PageInfo<GpsStocktake>(gpsStocktakeList);
		return page;
	}
}
