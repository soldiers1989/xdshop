package com.pujjr.gps.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pujjr.common.exception.CheckFailException;
import com.pujjr.gps.api.AffirmReceiveParam;
import com.pujjr.gps.api.ExpressDetailParam;
import com.pujjr.gps.api.ReceiveParam;
import com.pujjr.gps.dal.domain.ExpressReceipt;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsReceive;

/**
 * @author wen
 * @date 创建时间：2017年11月30日 下午5:09:08
 *
 */
public interface ReceiveService {

	/**
	 * 根据订单号查询全部收获清单
	 * 
	 * @param orderId
	 * @return
	 */
	GpsOrder selectAllReceiveByOrderId(String orderId);

	/**
	 * 根据申请单号查询全部申请单相关信息
	 * 
	 * @param applyId
	 * @return
	 */
	GpsApply selectAllReceiveByApplyId(String applyId);

	/**
	 * 提交
	 * 
	 * @param gpsReceiveList
	 * @return
	 * @throws CheckFailException
	 */
	List<GpsReceive> submitGpsApply(List<GpsReceive> gpsReceiveList) throws CheckFailException;

	/**
	 * 暂存
	 * 
	 * @param gpsReceiveList
	 * @return
	 */
	List<GpsReceive> saveGpsReceive(List<GpsReceive> gpsReceiveList);

	/**
	 * 获取快递单列表
	 * 
	 * @param applyId
	 *            快递单号
	 * @return
	 */
	List<ExpressReceipt> getExpressListByApplyId(String applyId);

	/**
	 * 提交确认收货
	 * 
	 * @param gpsReceiveList
	 */
	void commitReceive(AffirmReceiveParam receiveParam);

	
	/**
	 * 根据快递单号，查询收货信息
	 * @param expressNo
	 * @return
	 */
	PageInfo<GpsReceive> getGpsReceiveByExpressNo(ExpressDetailParam expressDetailParam,String expressNo);
	

	/**
	 * 保存或更新收货清单
	 * 
	 * @param receiveParam
	 * @return
	 */
	GpsReceive saveReceive(ReceiveParam receiveParam);

	/**
	 * 添加收获清单
	 * 
	 * @param receiveParam
	 * @return
	 */
	GpsReceive addReceive(ReceiveParam receiveParam);

	/**
	 * 更新收货清单
	 * 
	 * @param receiveParam
	 * @return
	 */
	GpsReceive updateReceive(ReceiveParam receiveParam);

	/**
	 * 删除收货清单
	 * 
	 * @param receiveId
	 * @param operUserId
	 * @return
	 */
	int deleteReceive(String receiveId, String operUserId);
	
	/**
	 * 经销商确认收货，gps入库
	 * @param receiveParam
	 * @return
	 */
	public String receiveGps(AffirmReceiveParam receiveParam);

}
