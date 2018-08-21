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
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsStorePj;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.vo.GpsInstallHisVo;
import com.pujjr.gps.vo.GpsInstallPjVo;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.GpsStorePjVo;
import com.pujjr.gps.vo.StoreInfoVo;

import io.swagger.annotations.ApiOperation;

/**
 * 库存管理控制层
 * 
 * @author tom
 *
 */
@RestController
@RequestMapping("/store")
public class StoreController extends BaseController {

	@Autowired
	private StoreService storeServiceImpl;

	@GetMapping(value = "/pj/list")
	@ApiOperation(value = "获取潽金库存列表")
	public PageInfo<GpsStorePjVo> getPjStore(StorePjParam storePjParam) {
		try {
			PageInfo<GpsStorePjVo> pageInfo = storeServiceImpl.getPjStore(storePjParam);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/pj/save/single")
	@ApiOperation(value = "单笔新增潽金库存")
	public GpsStorePj savePjStore(@RequestBody GpsInstallPjVo gpsInstallPjVo) {
		try {
			GpsStorePj gpsStorePj = storeServiceImpl.savePjStore(gpsInstallPjVo);
			return gpsStorePj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/pj/delete/{storeId}")
	@ApiOperation(value = "删除潽金库存")
	public GpsStorePj delPjStore(@PathVariable String storeId) {
		try {
			GpsStorePj gpsStorePj = storeServiceImpl.delPjStore(storeId);
			return gpsStorePj;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping("/pj/add/batch")
	@ApiOperation(value = "批量新增潽金库存")
	public List<GpsStorePjVo> batchAddPjStore(MultipartFile file, String consigner) throws Exception {
		Assert.notNull(file, "上传文件为空");
		// List<GpsSend> list = sendService.saveGpsSendListByExcelFile(file.getInputStream(), consigner);
		List<GpsStorePjVo> list = storeServiceImpl.batchAddPjStore(file, consigner);
		return list;
	}

	@GetMapping(value = "/branch/list")
	@ApiOperation(value = "获取经销商库存列表")
	public PageInfo<GpsStoreBranchVo> getBranchStore(GpsStoreBranchVo gpsStoreBranchVo) {
		try {
			PageInfo<GpsStoreBranchVo> pageInfo = storeServiceImpl.getBranchStore(gpsStoreBranchVo);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/branch/save/single")
	@ApiOperation(value = "保存经销商库存")
	public GpsStoreBranch saveBranchStore(@RequestBody GpsInstallHisVo gpsInstallHisVo) {
		try {
			GpsStoreBranch gpsStoreBranch = storeServiceImpl.saveBranchStore(gpsInstallHisVo);
			return gpsStoreBranch;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/branch/delete/{storeId}")
	@ApiOperation(value = "删除经销商库存")
	public GpsStoreBranch delBranchStore(@PathVariable String storeId) {
		try {
			GpsStoreBranch gpsStoreBranch = storeServiceImpl.delBranchStore(storeId);
			return gpsStoreBranch;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping("/branch/add/batch")
	@ApiOperation(value = "批量新增经销商库存")
	public List<GpsStoreBranchVo> batchAddBranchStore(MultipartFile file, String consigner) throws Exception {
		Assert.notNull(file, "上传文件为空");
		// List<GpsSend> list = sendService.saveGpsSendListByExcelFile(file.getInputStream(), consigner);
		List<GpsStoreBranchVo> list = storeServiceImpl.batchAddBranchStore(file, consigner);
		return null;
	}

	@PostMapping(value = "/branch/disable/{gpsId}")
	@ApiOperation(value = "禁用经销商库存")
	public GpsStoreBranch disabelStoreBranch(@PathVariable String gpsId) {
		try {
			// GpsStoreBranch gpsStoreBranch = storeServiceImpl.singleAddBranchStore(gpsInstallHisVo);
			return storeServiceImpl.disabelStoreBranch(gpsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/pj/disable/{gpsId}")
	@ApiOperation(value = "禁用经销商库存")
	public GpsStorePj disabelStorePj(@PathVariable String gpsId) {
		try {
			// GpsStoreBranch gpsStoreBranch = storeServiceImpl.singleAddBranchStore(gpsInstallHisVo);
			return storeServiceImpl.disabelStorePj(gpsId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/branch/{branchId}/checkStore")
	@ApiOperation(value = "盘点经销商库存")
	public GpsStocktake checkStore(@PathVariable String branchId) {
		try {
			GpsStocktake stocktake = storeServiceImpl.tempCheckBranchStore(branchId);
			return stocktake;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/branch/checkAllStore")
	@ApiOperation(value = "单笔新增经销商库存")
	public int checkAllBranchStore() {
		try {
			return storeServiceImpl.checkAllBranchStore();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@GetMapping(value = "/branch/storeInfo")
	@ApiOperation(value = "经销商库存总量信息")
	public StoreInfoVo getStoreInfo(GpsStoreBranchVo gpsStoreBranchVo) {
		try {
			StoreInfoVo storeInfo = storeServiceImpl.getStoreInfo(gpsStoreBranchVo);
			return storeInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	@PostMapping(value = "/branch/unlock/{gpsId}")
	@ApiOperation(value = "解锁GPS(经销商库存)")
	public GpsStoreBranch unlock(@PathVariable String gpsId) {
		logger.info("解锁:" + gpsId);
		GpsStoreBranch gpsStoreBranch = storeServiceImpl.unlock(gpsId);
		return gpsStoreBranch;
	}
	
	@PostMapping(value = "/pj/unlock/{gpsId}")
	@ApiOperation(value = "解锁GPS(潽金库存)")
	public GpsStorePj unlockPj(@PathVariable String gpsId) {
		logger.info("解锁:" + gpsId);
		GpsStorePj gpsStore = storeServiceImpl.unlockPj(gpsId);
		return gpsStore;
	}

}
