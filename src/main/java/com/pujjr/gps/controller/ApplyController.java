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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.bean.BeanPropertyUtils;
import com.pujjr.gps.api.ApplyParam;
import com.pujjr.gps.api.ApplySearchParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.enumeration.StocktakeType;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.vo.GpsApplyVo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("/apply")
public class ApplyController extends BaseController {

	@Autowired
	ApplyService applyService;

	@ResponseBody
	@GetMapping(value = "/{applyId}")
	@ApiOperation(value = "获取申请单详情")
	public GpsApply getApply(@ApiParam @PathVariable("applyId") String applyId) {
		try {
			GpsApply gpsApply = applyService.selectDetailByApplyId(applyId);
			return gpsApply;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/list")
	@ApiOperation(value = "获取申请单列表")
	public PageInfo<GpsApply> list(ApplySearchParam applySearchParam, String applyStatus) {
		try {
			BeanPropertyUtils.blank2Null(applySearchParam);
			PageInfo<GpsApply> pageInfo = applyService.gpsApplyList(applySearchParam, applyStatus);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "暂存申请单")
	public GpsApply save(@RequestBody ApplyParam applyParam) {
		try {
			GpsApply gpsApply = applyService.tempSaveGpsApply(applyParam,StocktakeType.SINGLE);
			return gpsApply;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/submit")
	@ApiOperation(value = "提交申请单")
	public GpsApply submit(@RequestBody ApplyParam applyParam) {
		try {
			GpsApply gpsApply = applyService.submitGpsApply(applyParam,StocktakeType.SINGLE);
			return gpsApply;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/list/waitApply")
	@ApiOperation(value = "查询可选申请单")
	public PageInfo<GpsApplyVo> waitApply(ApplySearchParam applySearchParam, String applyStatus) {
		try {
			BeanPropertyUtils.blank2Null(applySearchParam);
			PageInfo<GpsApplyVo> page = applyService.getWaitApplyList(applySearchParam, applyStatus);
//			PageInfo<GpsApply> page = applyService.gpsApplyList(applySearchParam, applyStatus);
			return page;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/submitAuto")
	@ApiOperation(value = "提交申请单")
	public int submitAuto() {
		try {
			return applyService.autoApplySubmit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
