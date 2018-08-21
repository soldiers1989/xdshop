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
import com.pujjr.gps.api.AffirmReceiveParam;
import com.pujjr.gps.api.ExpressDetailParam;
import com.pujjr.gps.api.ReceiveParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.ExpressReceipt;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.ReceiveService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/receive")
public class ReceiveController extends BaseController {

	@Autowired
	ReceiveService receiveService;
	@Autowired
	OrderService orderService;
	@Autowired
	ApplyService applyServiceImpl;

	@GetMapping(value = "/all/{orderId}")
	@ApiOperation(value = "获取全部GPS收货清单列表")
	public GpsOrder selectAllReceiveByOrderId(@ApiParam @PathVariable("orderId") String orderId) {
		try {
			GpsOrder gpsOrder = receiveService.selectAllReceiveByOrderId(orderId);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/apply/{applyId}")
	@ApiOperation(value = "根据申请单编号，获取该申请全部相关信息")
	public GpsApply selectAllReceiveByApplyId(@ApiParam @PathVariable("applyId") String applyId) {
		try {
			GpsApply gpsApply = receiveService.selectAllReceiveByApplyId(applyId);
			return gpsApply;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/express/{applyId}")
	@ApiOperation(value = "根据申请单编号，获取该申请全部快递单列表信息")
	public List<ExpressReceipt> getExpressByApplyId(@ApiParam @PathVariable("applyId") String applyId) {
		try {
			List<ExpressReceipt> receiptList = receiveService.getExpressListByApplyId(applyId);
			return receiptList;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/commit")
	@ApiOperation(value = "提交确认收货")
	public AffirmReceiveParam commitReceive(@RequestBody AffirmReceiveParam receiveParam) {
		try {
			receiveService.commitReceive(receiveParam);
			//经销商确认收货，gps入经销商库存
			/*String detailId = receiveService.receiveGps(receiveParam);
			//更新订单明细状态
			GpsOrderDetail gpsOrderDetail = orderService.changeOrderDetailStatus(detailId);
			//更新订单状态
			if(gpsOrderDetail != null){
				GpsOrder gpsOrder = orderService.changeOrderStatus(gpsOrderDetail.getOrderId());
			}
			//更新申请单状态
			if(gpsOrderDetail != null){
				applyServiceImpl.changeGpsApplyStatus(gpsOrderDetail.getApplyId());
			}*/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return receiveParam;
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "保存或更新收货")
	public GpsReceive saveReceive(@RequestBody ReceiveParam receiveParam) throws Exception {
		try {
			return receiveService.saveReceive(receiveParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("保存或更新收货失败");
		}
	}	
			
	@GetMapping(value = "/express/{expressNo}/detail")
	@ApiOperation(value = "获取快递单详情")
	public PageInfo<GpsReceive> getExpressDetail(ExpressDetailParam expressDetailParam,@PathVariable String expressNo) {
		try {
			PageInfo<GpsReceive> pageInfo = receiveService.getGpsReceiveByExpressNo(expressDetailParam,expressNo);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/add")
	@ApiOperation(value = "新增收货")
	public GpsReceive addReceive(@RequestBody ReceiveParam receiveParam) {
		try {
			return receiveService.addReceive(receiveParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/update")
	@ApiOperation(value = "修改收货")
	public GpsReceive updateReceive(@RequestBody ReceiveParam receiveParam) {
		try {
			return receiveService.updateReceive(receiveParam);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/delete/{receiveId}/{operUserId}")
	@ApiOperation(value = "删除收货")
	public int updateReceive(@PathVariable String receiveId,@PathVariable String operUserId) {
		try {
			return receiveService.deleteReceive(receiveId, operUserId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
