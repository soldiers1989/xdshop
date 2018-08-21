package com.pujjr.gps.service;

import java.util.List;

import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.vo.GpsBranchVo;

public interface GpsService {
	public List<GpsBrand> getGpsBrandList();
	/**
	 * 获取所有经销商
	 * @return
	 */
	public List<GpsBranchVo> getAllBranch();
	
	/**
	 * 上传给赛格昨天出库的GPS数据
	 */
	public void uploadYesterdayDataToSeg();
	
	public void uploadAllDataToSeg();
}
