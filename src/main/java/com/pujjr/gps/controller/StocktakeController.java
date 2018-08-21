package com.pujjr.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.service.StocktakeService;

import io.swagger.annotations.ApiOperation;

/**
 * 库存管理控制层
 * 
 * @author tom
 *
 */
@RestController
@RequestMapping("/stocktake")
public class StocktakeController extends BaseController {
	@Autowired
	private StocktakeService stocktakeService;
	
	@GetMapping(value = "/list")
	@ApiOperation(value = "获取经销商库存批量盘点信息")
	public PageInfo<GpsStocktake> getStocktake(StocktakeParam stocktakeParam) {
		try {
			PageInfo<GpsStocktake> pageInfo = stocktakeService.selectGpsStoreTake(stocktakeParam);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
