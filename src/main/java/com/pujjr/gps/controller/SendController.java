/**
 * 
 */
package com.pujjr.gps.controller;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsSend;
import com.pujjr.gps.service.SendService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/send")
public class SendController extends BaseController {

	@Autowired
	SendService sendService;

	@GetMapping(value = "/list")
	@ApiOperation(value = "获取GPS发货明细列表")
	public PageInfo<GpsSend> list(String detailId, String pageNum, String pageSize) {
		try {
			PageInfo<GpsSend> pageInfo = sendService.gpsSendListByDetailId(detailId, NumberUtils.toInt(pageNum, 1), NumberUtils.toInt(pageSize, 10));
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/allSend")
	public GpsOrder selectAllSendByOrderId(String orderId) {
		try {
			GpsOrder gpsOrder = sendService.selectAllSendByOrderId(orderId);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 确认发货，导入GPS供应商发货反馈细信息
	 * @param file
	 * @param consigner
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/impGpsSend")
	public List<GpsSend> impGpsSend(MultipartFile file,String consigner) throws Exception {
		Assert.notNull(file, "上传文件为空");
		List<GpsSend> list = sendService.saveGpsSendListByExcelFile(file.getInputStream(), consigner);
		return list;
	}

	@PostMapping("/submit/{orderId}")
	public GpsOrder submit(@ApiParam @PathVariable("orderId") String orderId) throws Exception {
		try {
			GpsOrder gpsOrder = sendService.submitSend(orderId);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	

}
