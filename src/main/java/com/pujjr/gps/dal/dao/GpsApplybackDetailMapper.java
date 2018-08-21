package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;

public interface GpsApplybackDetailMapper extends SuperMapper<GpsApplybackDetail> {
	/**
	 * 查询指定退货申请单详情
	 * 
	 * @param applybackId
	 * @return 申请单详情
	 */
	List<GpsApplybackDetail> selectApplybackDetailByApplybackId(String applybackId);

	/**
	 * 统计未确认退货申请明细
	 * 
	 * @param applybackId
	 * @return
	 */
	@Select("select count(*) from t_gps_applyback_detail where applyback_id = #{applybackId} and applyback_detail_status = #{applybackDetailStatus}")
	int countNotAckApplybackDetail(@Param("applybackId") String applybackId, @Param("applybackDetailStatus") String applybackDetailStatus);
	/**
	 * 根据gps编码查询退货申请明细
	 * @param applybackDetailId
	 * @return
	 */
	GpsApplybackDetail selectApplybackDetailByGpsId(String applybackDetailId);
}