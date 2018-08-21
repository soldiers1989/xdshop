package com.pujjr.gps.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.ApplybackDetailParam;
import com.pujjr.gps.api.ApplybackParam;
import com.pujjr.gps.api.ApplybackSearchParam;
import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;
import com.pujjr.gps.dal.domain.GpsApplybackDetailhis;
import com.pujjr.gps.vo.ApplyBackGpsVo;
import com.pujjr.gps.vo.GpsApplybackVo;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface ApplybackService {

	/**
	 * 查询获得退货申请单列表
	 * 
	 * @param applySearchParam
	 * @param applyStatus
	 *            申请单状态
	 * @return
	 */
	PageInfo<GpsApplybackVo> gpsApplyList(ApplybackSearchParam applybackSearchParam, String applyStatus);

	/**
	 * GPS退货申请单暂存
	 * 
	 * @param applyParam
	 * @return
	 */
	GpsApplyback tempSaveGpsApplyback(ApplybackParam applyBackParam);

	/**
	 * 提交GPS退货申请
	 * 
	 * @param applyParam
	 * @return
	 */
	GpsApplyback submitGpsApplyback(ApplybackParam applyBackParam);

	/**
	 * 根据退货申请单号获取退货申请单
	 * 
	 * @param applyId
	 * @return
	 */
	GpsApplyback selectByApplybackId(String applybackId);

	/**
	 * 获取可退货gps列表
	 * 
	 * @param branchId
	 * @param branchStatus
	 *            经销商状态：在线、下线
	 * @return
	 */
	public List<ApplyBackGpsVo> getWaitBackGpsList(String branchId, String branchStatus);

	/**
	 * 获取已选择gps
	 * 
	 * @param applybackId
	 * @return
	 */
	public List<ApplyBackGpsVo> getSelectedBackGpsList(String applybackId);

	/**
	 * 删除退货申请明细
	 * 
	 * @param applybackDetailId
	 * @param operUserId
	 * @return
	 */
	public void deleteApplybackDetail(String applybackDetailId, String operUserId, String remark);

	/**
	 * 确认退货申请
	 * 
	 * @param applybackDetailId
	 * @param operUserId
	 * @return
	 */
	public GpsApplybackDetail ackApplybackDetail(String applybackDetailId, String operUserId);

	/**
	 * 丢弃退货申请明细的GPS
	 * 
	 * @param applybackDetailId
	 * @param operUserId
	 */
	public GpsApplybackDetail missApplybackDetail(String applybackDetailId, String operUserId);

	/**
	 * 修改退货申请明细
	 * 
	 * @param applybackDetailParam
	 * @param operUserId
	 * @return
	 */
	public GpsApplybackDetail updateApplybackDetail(ApplybackDetailParam applybackDetailParam, String operUserId);

	/**
	 * 获得订单下的被删除记录列表
	 * 
	 * @param applybackId
	 * @return
	 */
	public List<GpsApplybackDetailhis> selectHisByApplybackId(String applybackId);
	
	public GpsApplybackDetail saveGpsApplybackDetail(GpsApplybackDetail gpsApplybackDetail);

}
