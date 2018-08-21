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
import com.pujjr.common.utils.bean.BeanPropertyUtils;
import com.pujjr.gps.api.OrderParam;
import com.pujjr.gps.api.OrderSearchParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.service.OrderService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	OrderService orderService;

	@GetMapping(value = "/{orderId}")
	@ApiOperation(value = "获取订单列表")
	public GpsOrder getOrder(@ApiParam @PathVariable("orderId") String orderId) {
		try {
			GpsOrder gpsOrder = orderService.selectByOrderId(orderId);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@GetMapping(value = "/list")
	@ApiOperation(value = "获取订单列表")
	public PageInfo<GpsOrder> list(OrderSearchParam orderSearchParam, String orderStatus) {
		try {
			BeanPropertyUtils.blank2Null(orderSearchParam);
			PageInfo<GpsOrder> pageInfo = orderService.gpsOrderList(orderSearchParam, orderStatus);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/save")
	@ApiOperation(value = "暂存订单")
	public GpsOrder save(@RequestBody OrderParam orderParam) {
		try {
			//回归申请单数据
			orderService.rollbackOldSelectedApplyGpsNum(orderParam);
			GpsOrder gpsOrder = orderService.tempSaveGpsOrder(orderParam);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	@PostMapping(value = "/submit")
	@ApiOperation(value = "提交订单")
	public GpsOrder submit(@RequestBody OrderParam orderParam) {
		try {
			//回归申请单数据
			orderService.rollbackOldSelectedApplyGpsNum(orderParam);
			GpsOrder gpsOrder = orderService.submitGpsOrder(orderParam);
			return gpsOrder;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	

}
