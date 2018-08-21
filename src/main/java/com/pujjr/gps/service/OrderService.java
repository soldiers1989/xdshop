package com.pujjr.gps.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.OrderDetailParam;
import com.pujjr.gps.api.OrderParam;
import com.pujjr.gps.api.OrderSearchParam;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsSupplier;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface OrderService {

	/**
	 * 提交订单
	 * 
	 * @param orderParam
	 * @return
	 */
	GpsOrder submitGpsOrder(OrderParam orderParam);

	/**
	 * 暂存订单
	 * 
	 * @param orderParam
	 * @return
	 */
	GpsOrder tempSaveGpsOrder(OrderParam orderParam);

	/**
	 * 搜索订单列表
	 * 
	 * @param orderSearchParam
	 * @return
	 */
	PageInfo<GpsOrder> gpsOrderList(OrderSearchParam orderSearchParam, String orderStatus);

	/**
	 * 根据订单id查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	GpsOrder selectByOrderId(String orderId);

	/**
	 * 创建订单详细列表
	 * 
	 * @param gpsOrder
	 * @param orderDetailParamList
	 * @return
	 */
	GpsOrder saveOrderDetail(GpsOrder gpsOrder, List<OrderDetailParam> orderDetailParamList);

	/**
	 * 根据供应商编号获取供应商
	 * 
	 * @param supplierCode
	 * @return
	 */
	GpsSupplier selectSupplierBySupplierId(String supplierCode);

	/**
	 * 获取供应商列表
	 * 
	 * @return
	 */
	List<GpsSupplier> selectAllSupplier();

	/**
	 * 根据订单编号获取订单详情列表
	 * 
	 * @param orderId
	 * @return
	 */
	List<GpsOrderDetail> selectOrderDetailListByOrderId(String orderId);
	
	/**
	 * 更新订单明细状态
	 * @param orderDetailId
	 */
	public GpsOrderDetail changeOrderDetailStatus(String orderDetailId);
	
	/**
	 * 更新订单状态
	 * @param orderId
	 */
	public GpsOrder changeOrderStatus(String orderId);
	
	/**
	 * 回归原来老的已选申请单数量信息
	 * @author tom
	 * @time 2017年12月27日 下午7:23:26
	 * @param orderParam
	 */
	public void rollbackOldSelectedApplyGpsNum(OrderParam orderParam);

}
