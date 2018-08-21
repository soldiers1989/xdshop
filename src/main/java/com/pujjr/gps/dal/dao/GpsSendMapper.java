package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsSend;

public interface GpsSendMapper extends SuperMapper<GpsSend> {

	@Delete("delete from t_gps_send where detail_id = #{detailId}")
	void deleteByDetailId(String detailId);

	@Select("select * from t_gps_send where detail_id = #{detailId}")
	public List<GpsSend> selectByDetailId(String detailId);
	/**
	 * 根据发货状态查询发货表记录
	 * @author tom
	 * @time 2017年12月27日 下午3:17:44
	 * @param isSend 1：已发货；0：未发货
	 * @return 查询结果列表
	 */
	public List<GpsSend> selectAllSendByIsSend(int isSend);
}