package com.pujjr.gps.dal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.api.ApplySearchParam;
import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsApplyDo;

public interface GpsApplyMapper extends SuperMapper<GpsApply> {
	/**
	 * 查询可选申请单列表
	 * 
	 * @return
	 */
	List<GpsApplyDo> selectWaitApplyList(@Param("applySearchParam") ApplySearchParam applySearchParam,@Param("applyStatus") String applyStatus);

	/**
	 * 根据状态和类型查询申请单
	 * 
	 * @param applyType
	 * @param applyStatus
	 * @return
	 */
	@Select("SELECT * FROM t_gps_apply t WHERE t.apply_type = #{applyType} AND t.apply_status = #{applyStatus}")
	List<GpsApply> selectAutoApply(@Param("applyType") String applyType, @Param("applyStatus") String applyStatus);
	/**
	 * 获取经销商未完成申请单
	 * @param branchId
	 * @return
	 */
	List<GpsApply> selectAllUnFinishApply(String branchId);
	/**
	 * 多条件查询申请单信息
	 * @param paraMap
	 * @return
	 */
	List<GpsApply> selectAllApplyByParamMap(Map<String,Object> paraMap);
	
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	List<GpsApply> selectApplyByLinkmanId(String linkmanId);
	
	/**
	 * 查询未提交申请单
	 * @param branchId
	 * @return
	 */
	GpsApply selectUnCommitApply(String branchId);
	
}