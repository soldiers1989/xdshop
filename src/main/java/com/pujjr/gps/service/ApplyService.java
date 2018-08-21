package com.pujjr.gps.service;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.api.ApplyParam;
import com.pujjr.gps.api.ApplySearchParam;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.enumeration.StocktakeType;
import com.pujjr.gps.vo.GpsApplyVo;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface ApplyService {

	/**
	 * 查询获得GPS申请单列表
	 * 
	 * @param applySearchParam
	 * @return
	 */
	PageInfo<GpsApply> gpsApplyList(ApplySearchParam applySearchParam, String applyStatus);

	/**
	 * GPS申请单暂存
	 * 
	 * @param applyParam
	 * @param StocktakeType stocktakeType 盘点类型：批量盘点保存申请单、单笔保存申请单
	 * @return
	 */
	GpsApply tempSaveGpsApply(ApplyParam applyParam,StocktakeType stocktakeType);

	/**
	 * 提交GPS申请单
	 * 
	 * @param applyParam
	 * @return
	 */
	GpsApply submitGpsApply(ApplyParam applyParam,StocktakeType stocktakeType);

	/**
	 * 根据申请单号获取申请单
	 * 
	 * @param applyId
	 * @return
	 */
	GpsApply selectByApplyId(String applyId);

	/**
	 * 获取完整申请单信息
	 * 
	 * @param applyId
	 * @return
	 */
	GpsApply selectDetailByApplyId(String applyId);

	/**
	 * 根据申请单编号获取订单原始信息
	 * 
	 * @param applyId
	 * @return
	 */
	GpsApply selectBaseApplyByApplyId(String applyId);

	/**
	 * 根据联系人号获取联系人
	 * 
	 * @param linkmanId
	 * @return
	 */
	GpsLinkman selectLinkmanByLinkmanId(String linkmanId);

	/**
	 * 查询当前可选申请单（可选有线数量+无线数量>0）
	 * 
	 * @return
	 */
	PageInfo<GpsApplyVo> getWaitApplyList(ApplySearchParam applySearchParam, String applyStatus);

	/**
	 * 更改申请单出货订单数
	 * 
	 * @param applyId
	 * @param number
	 */
	void updateWaitReceiveOrderNum(String applyId, int number);

	/**
	 * 提交自动生成的订单
	 * 
	 * @return
	 */
	int autoApplySubmit();

	/**
	 * 一步登天
	 * 
	 * @param applyParam
	 * @return
	 */
	GpsApply meteoricRise(ApplyParam applyParam,StocktakeType stocktakeType);
	/**
	 * 更改申请单状态
	 * @param applyId 申请单号
	 * @return
	 */
	public GpsApply changeGpsApplyStatus(String applyId);

}
