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

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.ApplybackParam;
import com.pujjr.gps.api.ApplybackSearchParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;
import com.pujjr.gps.dal.domain.GpsApplybackDetailhis;
import com.pujjr.gps.service.ApplybackService;
import com.pujjr.gps.vo.ApplyBackGpsVo;
import com.pujjr.gps.vo.GpsApplybackVo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/applyback")
public class ApplybackController extends BaseController {
	@Autowired
	private ApplybackService applybackServiceImpl;

	@PostMapping(value = "/save")
	@ApiOperation(value = "暂存退货申请单")
	public GpsApplyback saveApplyback(@RequestBody ApplybackParam applybackParam) {
		try {
			GpsApplyback gpsApplyback = applybackServiceImpl.tempSaveGpsApplyback(applybackParam);
			return gpsApplyback;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/commit")
	@ApiOperation(value = "提交退货申请单")
	public GpsApplyback commitApplyback(@RequestBody ApplybackParam applybackParam) {
		try {
			GpsApplyback gpsApplyback = applybackServiceImpl.submitGpsApplyback(applybackParam);
			return gpsApplyback;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 查询可选gps列表
	 * 
	 * @param branchId
	 *            经销商编码
	 * @param branchStatus
	 *            经销商状态
	 * @return
	 */
	@GetMapping(value = "/list/waitChooseGps/{branchId}/{branchStatus}")
	@ApiOperation(value = "查询可选gps")
	public List<ApplyBackGpsVo> waitChooseGps(@ApiParam @PathVariable String branchId, @ApiParam @PathVariable String branchStatus) {
		try {
			List<ApplyBackGpsVo> gpsList = applybackServiceImpl.getWaitBackGpsList(branchId, branchStatus);
			// PageInfo<GpsApplyVo> page = applyService.getWaitApplyList(applySearchParam,applyStatus);
			return gpsList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 查询已选gps列表
	 * 
	 * @param applybackId
	 *            退货申请单编号
	 * @return
	 */
	@GetMapping(value = "/list/selectedGps")
	@ApiOperation(value = "查询已选gps")
	public List<ApplyBackGpsVo> selectedGps(String applybackId) {
		try {
			List<ApplyBackGpsVo> gpsList = applybackServiceImpl.getSelectedBackGpsList(applybackId);
			return gpsList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/*
	 * @GetMapping(value = "/list/waitApplyback")
	 * @ApiOperation(value = "查询可选申请单") public PageInfo<GpsApplyback> waitApply(ApplySearchParam applySearchParam,String branchId,String applyStatus) { try { // PageInfo<GpsApplyVo> page = applyService.getWaitApplyList(applySearchParam,applyStatus); return null; } catch (Exception e) { logger.error(e.getMessage(), e); throw e; } }
	 */

	/**
	 * 
	 * @param applySearchParam
	 * @param branchId
	 * @param applyStatus
	 * @return
	 */
	@GetMapping(value = "/list")
	@ApiOperation(value = "查询退货申请单列表")
	public PageInfo<GpsApplybackVo> getApplyList(ApplybackSearchParam applybackSearchParam, String applyStatus) {
		try {
			PageInfo<GpsApplybackVo> pageInfo = applybackServiceImpl.gpsApplyList(applybackSearchParam, applyStatus);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/check/commit")
	@ApiOperation(value = "提交退货申请单")
	public GpsApplyback commitCheck(@RequestBody ApplybackParam applybackParam) {
		try {
			GpsApplyback gpsApplyback = applybackServiceImpl.submitGpsApplyback(applybackParam);
			return gpsApplyback;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/{applybackDetailId}/ack/{operUserId}")
	@ApiOperation(value = "确认退货申请明细")
	public GpsApplybackDetail ackApplybackDetail(@PathVariable String applybackDetailId,@PathVariable String operUserId) {
		try {
			GpsApplybackDetail gpsApplybackDetail = applybackServiceImpl.ackApplybackDetail(applybackDetailId, operUserId);
			return gpsApplybackDetail;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/{applybackDetailId}/miss/{operUserId}")
	@ApiOperation(value = "退货申请明细GPS丢弃")
	public GpsApplybackDetail missApplybackDetail(@PathVariable String applybackDetailId, @PathVariable String operUserId) {
		try {
			GpsApplybackDetail gpsApplybackDetail = applybackServiceImpl.missApplybackDetail(applybackDetailId, operUserId);
			return gpsApplybackDetail;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/{applybackDetailId}/delete/{operUserId}/{remark}")
	@ApiOperation(value = "提交退货申请单")
	public void deleteApplybackDetail(@PathVariable String applybackDetailId,@PathVariable String operUserId,@PathVariable String remark) {
		try {
			applybackServiceImpl.deleteApplybackDetail(applybackDetailId, operUserId, remark);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/{applybackId}/his")
	@ApiOperation(value = "获得退货订单下被删除的历史记录")
	public List<GpsApplybackDetailhis> selectHisByApplybackId(@PathVariable String applybackId) {
		try {
			return applybackServiceImpl.selectHisByApplybackId(applybackId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/detail/save")
	@ApiOperation(value = "保存退回申请单明细")
	public GpsApplybackDetail saveApplybackDetail(@RequestBody GpsApplybackDetail gpsApplybackDetail) {
		try {
			return applybackServiceImpl.saveGpsApplybackDetail(gpsApplybackDetail);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
}
