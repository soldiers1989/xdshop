/**
 * 
 */
package com.pujjr.gps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.LockParam;
import com.pujjr.gps.api.StoreBranchParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.service.GpsService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.vo.GpsStoreBranchVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/gps")
@Api("外部接口")
public class GpsApiController extends BaseController {

	@Autowired
	OrderService orderService;
	@Autowired
	StoreService storeService;
	@Autowired
	private GpsService gpsServiceImpl;
	
	@GetMapping(value = "/brand/list")
	@ApiOperation(value = "获取品牌列表")
	public List<GpsBrand> getApply() {
		try {
			List<GpsBrand> gpsBrandList = gpsServiceImpl.getGpsBrandList();
			return gpsBrandList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GetMapping(value = "/supplier/list")
	@ApiOperation(value = "获取供应商列表")
	public List<GpsSupplier> list() {
		List<GpsSupplier> list = orderService.selectAllSupplier();
		return list;
	}

	@GetMapping(value = "/gpsInfo/list")
	@ApiOperation(value = "获得可用GPS信息")
	public PageInfo<GpsStoreBranchVo> gpsInfo(StoreBranchParam storeBranchParam) {
		logger.info("获得可用GPS信息:" + JSON.toJSONString(storeBranchParam));
		PageInfo<GpsStoreBranchVo> pageInfo = storeService.getBaseBranchStore(storeBranchParam);
		return pageInfo;

	}

	@PostMapping(value = "/batch")
	@ApiOperation(value = "批量处理gps")
	public List<GpsStoreBranch> batch(@RequestBody LockParam lockParm) {
		Assert.notNull(lockParm, "请求对象不能为空");
		List<GpsStoreBranch> gpsStoreBranchList = storeService.batchLock(lockParm);
		return gpsStoreBranchList;
	}

	@GetMapping(value = "/lock/{gpsId}/{appId}")
	@ApiOperation(value = "锁定gps")
	public GpsStoreBranch lock(@PathVariable String gpsId,@PathVariable String appId) {
		logger.info("锁定:" + gpsId);
		GpsStoreBranch gpsStoreBranch = storeService.lock(gpsId, appId);
		return gpsStoreBranch;
	}

	@GetMapping(value = "/unlock/{gpsId}")
	@ApiOperation(value = "解锁GPS")
	public GpsStoreBranch unlock(@PathVariable String gpsId) {
		logger.info("解锁:" + gpsId);
		GpsStoreBranch gpsStoreBranch = storeService.unlock(gpsId);
		return gpsStoreBranch;
	}

	@PostMapping(value = "/outStore")
	@ApiOperation(value = "出库GPS")
	public GpsStoreBranch outStore(@RequestBody String gpsId) {
		logger.info("出库:" + gpsId);
		GpsStoreBranch gpsStoreBranch = storeService.outStore(gpsId);
		return gpsStoreBranch;
	}

	@PostMapping(value = "/recover")
	@ApiOperation(value = "恢复GPS")
	public GpsStoreBranch recover(String gpsId) {
		logger.info("恢复:" + gpsId);
		GpsStoreBranch gpsStoreBranch = storeService.recover(gpsId);
		return gpsStoreBranch;
	}

}