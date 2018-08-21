package com.pujjr.gps.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.GpsService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;

/**
 * @author wen
 * @date 创建时间：2017年12月5日 下午6:55:10
 *
 */
@Service
@EnableScheduling
public class TaskScheduler extends BaseService<TaskScheduler> {

	@Value("${schedul.start:false}")
	private Boolean isSchedulStart;

	@Autowired
	private ApplyService applyService;

	@Autowired
	private GpsApplyMapper gpsApplyMapper;

	@Autowired
	private StoreService StoreService;
	
	@Autowired
	private GpsService gpsService;

	/**
	 * 定时盘点，并自动生成各经销商采购申请单
	 */
	@Scheduled(cron = "${schedul.takeStock.cronTime}") 
	public void checkAllBranchStore() {
		logger.info("开始每月库存盘点");
		if (isSchedulStart) {
			StoreService.checkAllBranchStore();
		}
	}

	/**
	 * 申请单自动提交
	 * 提交规则：T+1日提交
	 */
	@Scheduled(cron = "${schedul.autoCommit.cronTime}") 
	public void autoApplySubmit() {
		logger.info("开始提交当月未提交自动申请单");
		if (isSchedulStart) {
			applyService.autoApplySubmit();
		}
	}
	
	
	@Scheduled(cron = "${schedul.autoUploadToSeg.cronTime}") 
	public void autoUploadToSeg() {
		logger.info("上传数据到赛格");
		if (isSchedulStart) {
			gpsService.uploadYesterdayDataToSeg();
		}
	}
	
	public static boolean uploadOnce = false;
	
	@Scheduled(cron = "${schedul.autoAllDataUploadToSeg.cronTime}") 
	public void autoUploadAllDataToSeg() {
		logger.info("上传所有数据到赛格");
		if (isSchedulStart && uploadOnce) {
			gpsService.uploadAllDataToSeg();
		}
	}
}
