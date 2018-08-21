package com.pujjr.gps.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.CheckhisParam;
import com.pujjr.gps.api.OrderDetailParam;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsApplybackMapper;
import com.pujjr.gps.dal.dao.GpsCheckhisMapper;
import com.pujjr.gps.dal.dao.GpsOrderMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.enumeration.ApplyStatus;
import com.pujjr.gps.enumeration.CheckhisNode;
import com.pujjr.gps.enumeration.OrderStatus;
import com.pujjr.gps.service.CheckhisService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.vo.GpsCheckhisVo;

/**
 * 审核审批服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class CheckhisServiceImpl extends BaseService<CheckhisService> implements CheckhisService {

	@Autowired
	GpsApplyMapper gpsApplyMapper;

	@Autowired
	GpsOrderMapper gpsOrderMapper;

	@Autowired
	OrderService orderService;

	@Autowired
	GpsCheckhisMapper gpsCheckhisMapper;
	@Autowired
	GpsApplybackMapper gpsApplybackMapper;

	@Override
	public List<GpsCheckhis> selectByPubId(String pubId) {
		Assert.isTrue(StringUtils.isNotBlank(pubId), "申请单编号或订单编号不能为空");
		List<GpsCheckhis> list = gpsCheckhisMapper.selectByPubId(pubId);
		return list;
	}

	@Override
	public GpsCheckhis submitApply(GpsApply gpsApply) {
		Assert.notNull(gpsApply, "申请单信息获取错误");
		CheckhisParam checkhisParam = new CheckhisParam();
		checkhisParam.setPubId(gpsApply.getApplyId());
		checkhisParam.setPubStatus(gpsApply.getApplyStatus());
		checkhisParam.setWireNum(gpsApply.getWireNum());
		checkhisParam.setWirelessNum(gpsApply.getWirelessNum());
		checkhisParam.setComment(gpsApply.getRemark());
		checkhisParam.setCreateAccountId(gpsApply.getCreateAccountId());
		// 参数内容检查
		paramContentCheck(checkhisParam);
		// 创建审核审批记录
		GpsCheckhis gpsCheckhis = saveGpsCheckhis(checkhisParam, CheckhisNode.APPLY_SUBMITTED);
		return gpsCheckhis;
	}

	@Override
	public GpsCheckhis checkApply(CheckhisParam checkhisParam) {
		// 参数内容检查
		Assert.notNull(checkhisParam, "请求数据获取错误");
		Assert.isTrue(ApplyStatus.contains(checkhisParam.getPubStatus()), "申请单订单状态参数错误");
		Assert.isTrue((StringUtils.equals(checkhisParam.getPubStatus(), ApplyStatus.CHECK_PASS.getCode()) || StringUtils.equals(checkhisParam.getPubStatus(), ApplyStatus.CHECK_REJECT.getCode())), "审核状态改变错误,无法改变为状态" + checkhisParam.getPubStatus());
		paramContentCheck(checkhisParam);
		// 获取申请单记录
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(checkhisParam.getPubId());
		Assert.notNull(gpsApply, "无此申请单记录");
		Assert.isTrue(StringUtils.equals(gpsApply.getApplyStatus(), ApplyStatus.SUBMITTED.getCode()), "只能审核状态为【已提交】的申请单");
		// 创建审核审批记录
		GpsCheckhis gpsCheckhis = saveGpsCheckhis(checkhisParam, CheckhisNode.APPLY_CHECK);
		// 改变订单申请单状态;
		updateGpsApplyStatus(checkhisParam, gpsApply);
		logger.info("申请单审核记录:" + JSON.toJSONString(gpsCheckhis));
		return gpsCheckhis;
	}

	@Override
	public GpsCheckhis approveApply(CheckhisParam checkhisParam) {
		// 参数内容检查
		Assert.notNull(checkhisParam, "请求数据获取错误");
		Assert.isTrue(ApplyStatus.contains(checkhisParam.getPubStatus()), "申请单订单状态参数错误");
		Assert.isTrue((StringUtils.equals(checkhisParam.getPubStatus(), ApplyStatus.APPROVE_PASS.getCode()) || StringUtils.equals(checkhisParam.getPubStatus(), ApplyStatus.APPROVE_REJECT.getCode())), "审批状态改变错误,无法改变为状态" + checkhisParam.getPubStatus());
		paramContentCheck(checkhisParam);
		// 获取申请单记录
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(checkhisParam.getPubId());
		Assert.notNull(gpsApply, "无此申请单记录");
		Assert.isTrue(StringUtils.equals(gpsApply.getApplyStatus(), ApplyStatus.CHECK_PASS.getCode()), "只能审批状态为【审核通过】的申请单");
		// 创建审核审批记录
		GpsCheckhis gpsCheckhis = saveGpsCheckhis(checkhisParam, CheckhisNode.APPLY_APPROVE);
		// 改变订单申请单状态;
		updateGpsApplyStatus(checkhisParam, gpsApply);
		logger.info("申请单审批记录:" + JSON.toJSONString(gpsCheckhis));
		return gpsCheckhis;
	}

	@Override
	public GpsCheckhis submitOrder(GpsOrder gpsOrder) {
		Assert.notNull(gpsOrder, "订单信息获取错误");
		CheckhisParam checkhisParam = new CheckhisParam();
		checkhisParam.setPubId(gpsOrder.getOrderId());
		checkhisParam.setPubStatus(gpsOrder.getOrderStatus());
		checkhisParam.setWireNum(gpsOrder.getWireNum());
		checkhisParam.setWirelessNum(gpsOrder.getWirelessNum());
		checkhisParam.setComment(null);
		checkhisParam.setCreateAccountId(gpsOrder.getCreateAccountId());
		// 参数内容检查
		paramContentCheck(checkhisParam);
		// 创建审核审批记录
		GpsCheckhis gpsCheckhis = saveGpsCheckhis(checkhisParam, CheckhisNode.ORDER_SUBMITTED);
		return gpsCheckhis;
	}

	@Override
	public GpsCheckhis approveOrder(CheckhisParam checkhisParam) {
		// 参数内容检查
		Assert.notNull(checkhisParam, "请求数据获取错误");
		Assert.isTrue((StringUtils.equals(checkhisParam.getPubStatus(), OrderStatus.APPROVE_PASS.getCode()) || StringUtils.equals(checkhisParam.getPubStatus(), OrderStatus.APPROVE_REJECT.getCode())), "审批状态改变错误,无法改变为状态" + checkhisParam.getPubStatus());
		paramContentCheck(checkhisParam);
		// 获取订单记录
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(checkhisParam.getPubId());
		Assert.notNull(gpsOrder, "无此申请单记录");
		Assert.isTrue(StringUtils.equals(gpsOrder.getOrderStatus(), OrderStatus.SUBMITTED.getCode()), "只能审批已提交的订单");
		// 创建审核审批记录
		GpsCheckhis gpsCheckhis = saveGpsCheckhis(checkhisParam, CheckhisNode.APPLY_APPROVE);
		// 改变订单申请单状态;
		updateGpsOrderStatus(gpsOrder, checkhisParam);
		logger.info("订单审批记录:" + JSON.toJSONString(gpsCheckhis));
		return gpsCheckhis;
	}

	public void updateGpsOrderStatus(GpsOrder gpsOrder, CheckhisParam checkhisParam) {
		List<OrderDetailParam> orderDetailParamList = checkhisParam.getOrderDetailParamList();
		gpsOrder.setOrderStatus(checkhisParam.getPubStatus());
		gpsOrder = orderService.saveOrderDetail(gpsOrder, orderDetailParamList);
	}

	/**
	 * @param checkhisParam
	 * @param gpsApply
	 */
	public void updateGpsApplyStatus(CheckhisParam checkhisParam, GpsApply gpsApply) {
		//有线总量
		gpsApply.setTotalWireNum(checkhisParam.getWireNum());
		//无线总量
		gpsApply.setTotalWirelessNum(checkhisParam.getWirelessNum());
		//有线可用总量
		gpsApply.setFreeWireNum(checkhisParam.getWireNum());
		//无线可用总量
		gpsApply.setFreeWirelessNum(checkhisParam.getWirelessNum());
		//状态
		gpsApply.setApplyStatus(checkhisParam.getPubStatus());
		gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
	}

	/**
	 * 保存
	 * 
	 * @param checkhisParam
	 * @return
	 */
	public GpsCheckhis saveGpsCheckhis(CheckhisParam checkhisParam, CheckhisNode checkhisNode) {
		GpsCheckhis gpsCheckhis = new GpsCheckhis();
		gpsCheckhis.setId(BaseUtils.get16UUID());
		gpsCheckhis.setPubId(checkhisParam.getPubId());
		gpsCheckhis.setNodeId(checkhisNode.getCode());
		gpsCheckhis.setWireNum(checkhisParam.getWireNum());
		gpsCheckhis.setWirelessNum(checkhisParam.getWirelessNum());
		gpsCheckhis.setPubStatus(checkhisParam.getPubStatus());
		gpsCheckhis.setComment(checkhisParam.getComment());
		gpsCheckhis.setCreateAccountId(checkhisParam.getCreateAccountId());
		gpsCheckhis.setCreateTime(new Date());
		gpsCheckhisMapper.insert(gpsCheckhis);
		return gpsCheckhis;
	}

	/**
	 * 参数内容检查
	 */
	public void paramContentCheck(CheckhisParam checkhisParam) {
		Assert.isTrue(StringUtils.isNotBlank(checkhisParam.getPubId()), "申请单订单编号不能为空");
		Assert.isTrue(StringUtils.length(checkhisParam.getComment()) < GpsApply.REMARK_LENGTH, "意见长度过长");
		Assert.isTrue((checkhisParam.getWireNum() != null && checkhisParam.getWireNum() >= 0), "有线GPS数错误");
		Assert.isTrue((checkhisParam.getWirelessNum() != null && checkhisParam.getWirelessNum() >= 0), "无线GPS数错误");
		Assert.isTrue((checkhisParam.getWireNum() + checkhisParam.getWirelessNum()) > 0, "有线与无线GPS数量相加不能为0");
		Assert.isTrue(StringUtils.isNotBlank(checkhisParam.getCreateAccountId()), "申请单订单审批审核创建人不能为空");
	}

	@Override
	public GpsCheckhis checkApplyback(GpsCheckhisVo gpsCheckHisVo) {
		// 更改退货申请单状态
		String applybackId = gpsCheckHisVo.getPubId();
		GpsApplyback applyBack = gpsApplybackMapper.selectByPrimaryKey(applybackId);
		if (applyBack != null) {
			// TODO 新流程无退货审核审批 applyBack.setApplybackStatus(ApplybackStatus.CHECK_PASS.getCode());
		}
		gpsApplybackMapper.updateByPrimaryKeySelective(applyBack);

		// 记录审核记录
		GpsCheckhis checkHis = new GpsCheckhis();
		checkHis.setId(BaseUtils.get16UUID());
		checkHis.setPubId(applybackId);
		checkHis.setNodeId(gpsCheckHisVo.getNodeId());
		checkHis.setCreateAccountId(gpsCheckHisVo.getCreateAccountId());
		checkHis.setComment(gpsCheckHisVo.getComment());
		checkHis.setCreateTime(Calendar.getInstance().getTime());
		gpsCheckhisMapper.insertSelective(checkHis);
		logger.info("退货申请审核记录:" + JSON.toJSONString(checkHis));
		return checkHis;
	}

	@Override
	public GpsCheckhis approveApplyback(GpsCheckhisVo gpsCheckHisVo) {
		// 更改退货申请单状态
		String applybackId = gpsCheckHisVo.getPubId();
		GpsApplyback applyBack = gpsApplybackMapper.selectByPrimaryKey(applybackId);
		if (applyBack != null) {
			// TODO 新流程无退货审核审批 applyBack.setApplybackStatus(ApplybackStatus.APPROVE_PASS.getCode());
		}
		gpsApplybackMapper.updateByPrimaryKeySelective(applyBack);

		// 记录审核记录
		GpsCheckhis checkHis = new GpsCheckhis();
		checkHis.setId(BaseUtils.get16UUID());
		checkHis.setPubId(applybackId);
		checkHis.setNodeId(gpsCheckHisVo.getNodeId());
		checkHis.setCreateAccountId(gpsCheckHisVo.getCreateAccountId());
		checkHis.setComment(gpsCheckHisVo.getComment());
		checkHis.setCreateTime(Calendar.getInstance().getTime());
		gpsCheckhisMapper.insertSelective(checkHis);
		logger.info("退货申请审批记录:" + JSON.toJSONString(checkHis));
		return checkHis;
	}

	@Override
	public List<GpsCheckhis> getCheckHisByPubId(String pubId) {
		return gpsCheckhisMapper.selectByPubId(pubId);
	}

}
