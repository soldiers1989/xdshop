/**
 * 
 */
package com.pujjr.gps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.GpsLinkmanParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.service.GpsLinkmanService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * gps联系人控制层
 * @author tom
 *
 */
@RestController
@RequestMapping("/linkman")
public class GpsLinkmanController extends BaseController {

	@Autowired
	private GpsLinkmanService gpsLinkmanServiceImpl;
	
	@GetMapping(value = "/list")
	@ApiOperation(value = "获取经销商联系人列表")
	public PageInfo<GpsLinkman> getGpsLinkmanGps(GpsLinkmanParam gpsLinkmanParam) {
		try {
			return gpsLinkmanServiceImpl.getGpsLinkmanListByParam(gpsLinkmanParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/save")
	@ApiOperation(value = "保存经销商联系人")
	public GpsLinkman deleteGpsLinkMan(@RequestBody GpsLinkman gpsLinkman) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.saveGpsLinkman(gpsLinkman);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/setDefault")
	@ApiOperation(value = "设置/取消为默认联系人")
	public GpsLinkman setDefault(@RequestBody GpsLinkmanParam GpsLinkmanParam) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.setDefault(GpsLinkmanParam);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/delete")
	@ApiOperation(value = "删除默认联系人")
	public GpsLinkman doDelete(@RequestBody GpsLinkman gpsLinkman) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.deleteGpsLinkman(gpsLinkman);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GetMapping(value = "/default/{branchId}")
	@ApiOperation(value = "获取经销商默认联系人")
	public GpsLinkman doDelete(@ApiParam @PathVariable String branchId) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.getDefaultGpsLinkman(branchId);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/*@PostMapping(value = "/add")
	@ApiOperation(value = "新增经销商联系人")
	public GpsLinkman addGpsLinkMan(@RequestBody GpsLinkman gpsLinkman) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.addGpsLinkman(gpsLinkman);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/update")
	@ApiOperation(value = "修改经销山联系人")
	public GpsLinkman updateGpsLinkMan(@RequestBody GpsLinkman gpsLinkman) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.updateGpsLinkman(gpsLinkman);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/{linkmanId}/delete")
	@ApiOperation(value = "删除经销商联系人")
	public GpsLinkman deleteGpsLinkMan(@RequestBody GpsLinkman gpsLinkman) {
		try {
			GpsLinkman gpsLinkmanRet = gpsLinkmanServiceImpl.deleteGpsLinkman(gpsLinkman);
			return gpsLinkmanRet;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}*/
}
