/**
 * 
 */
package com.pujjr.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.common.result.ResultInfo;
import com.pujjr.gps.api.CheckhisParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.service.CheckhisService;
import com.pujjr.gps.vo.GpsCheckhisVo;

import io.swagger.annotations.ApiOperation;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/checkhis")
public class CheckhisController extends BaseController {

	@Autowired
	CheckhisService checkhisService;

	@PostMapping(value = "/apply/check")
	@ApiOperation(value = "审核申请单")
	public GpsCheckhis checkApply(@RequestBody CheckhisParam checkhisParam) {
		try {
			GpsCheckhis gpsCheckhis = checkhisService.checkApply(checkhisParam);
			return gpsCheckhis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/apply/approve")
	@ApiOperation(value = "审批申请单")
	public GpsCheckhis approveApply(@RequestBody CheckhisParam checkhisParam) {
		try {
			GpsCheckhis gpsCheckhis = checkhisService.approveApply(checkhisParam);
			return gpsCheckhis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/order/check")
	@ApiOperation(value = "审核订单", hidden = true)
	public ResultInfo<GpsCheckhis> checkOrder(CheckhisParam checkhisParam) {
		ResultInfo<GpsCheckhis> resultInfo = new ResultInfo<>();
		return resultInfo.fail("功能未开放");
	}

	@PostMapping(value = "/order/approve")
	@ApiOperation(value = "审批订单")
	public GpsCheckhis approveOrder(@RequestBody CheckhisParam checkhisParam) {
		try {
			GpsCheckhis gpsCheckhis = checkhisService.approveOrder(checkhisParam);
			return gpsCheckhis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/applyback/check")
	@ApiOperation(value = "审核退货申请单")
	public GpsCheckhis checkApplyback(@RequestBody GpsCheckhisVo gpsCheckHisVo) {
		try {
			System.out.println(gpsCheckHisVo);
			GpsCheckhis gpsCheckhis = checkhisService.checkApplyback(gpsCheckHisVo);
			return gpsCheckhis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/applyback/approve")
	@ApiOperation(value = "审批退货申请单")
	public GpsCheckhis approveApplyback(@RequestBody GpsCheckhisVo gpsCheckHisVo) {
		try {
			GpsCheckhis gpsCheckhis = checkhisService.approveApplyback(gpsCheckHisVo);
			return gpsCheckhis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GetMapping(value = "/list/{pubId}")
	@ApiOperation(value = "查询单号，查询审核审批历史")
	public List<GpsCheckhis> getCheckhisList(@PathVariable String pubId) {
		try {
			List<GpsCheckhis> checkHis = checkhisService.getCheckHisByPubId(pubId);
			return checkHis;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
