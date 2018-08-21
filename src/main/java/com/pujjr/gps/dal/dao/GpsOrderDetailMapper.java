package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsOrderDetail;

public interface GpsOrderDetailMapper extends SuperMapper<GpsOrderDetail> {

	@Delete("delete from t_gps_order_detail where order_id = #{orderId}")
	void deleteByOrderId(String orderId);

	@Select("select * from t_gps_order_detail where order_id = #{orderId}")
	public List<GpsOrderDetail> selectByOrderId(String orderId);

	@Select("select * from t_gps_order_detail where apply_id = #{applyId}")
	public List<GpsOrderDetail> selectByApplyId(String applyId);
	/**
	 * 更具申请单号查询订单详情
	 * @param applyId
	 * @return
	 */
	List<GpsOrderDetail> selectGpsOrderDetailByAppyId(String applyId);

}