package com.pujjr.gps.service;

import java.util.List;

import com.pujjr.gps.api.CheckhisParam;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.vo.GpsCheckhisVo;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface CheckhisService {
	/**
	 * 申请单提交
	 * 
	 * @param gpsApply
	 * @return
	 */
	GpsCheckhis submitApply(GpsApply gpsApply);

	/**
	 * 申请单审核
	 * 
	 * @param checkhisParam
	 * @return
	 */
	GpsCheckhis checkApply(CheckhisParam checkhisParam);

	/**
	 * 申请单审批
	 * 
	 * @param checkhisParam
	 * @return
	 */
	GpsCheckhis approveApply(CheckhisParam checkhisParam);

	/**
	 * 根据申请单或订单编号获取审批审核记录
	 * 
	 * @param pubId
	 * @return
	 */
	List<GpsCheckhis> selectByPubId(String pubId);

	/**
	 * 订单提交
	 * 
	 * @param gpsOrder
	 * @return
	 */
	GpsCheckhis submitOrder(GpsOrder gpsOrder);

	/**
	 * 订单审批
	 * 
	 * @param checkhisParam
	 * @param orderDetailParamList
	 * @return
	 */
	GpsCheckhis approveOrder(CheckhisParam checkhisParam);
	/**
	 * 退货申请审核
	 * @param checkhisParam
	 * @return
	 */
	GpsCheckhis checkApplyback(GpsCheckhisVo gpsCheckHisVo);
	
	/**
	 * 退货申请审批
	 * @param checkhisParam
	 * @return
	 */
	GpsCheckhis approveApplyback(GpsCheckhisVo gpsCheckHisVo);
	
	List<GpsCheckhis> getCheckHisByPubId(String pubId);
	
}
