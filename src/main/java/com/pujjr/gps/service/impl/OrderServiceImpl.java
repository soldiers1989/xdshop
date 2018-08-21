package com.pujjr.gps.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.BaseIterableUtils;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.OrderDetailParam;
import com.pujjr.gps.api.OrderParam;
import com.pujjr.gps.api.OrderSearchParam;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsOrderDetailMapper;
import com.pujjr.gps.dal.dao.GpsOrderMapper;
import com.pujjr.gps.dal.dao.GpsReceiveMapper;
import com.pujjr.gps.dal.dao.GpsSupplierMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.GpsStatus;
import com.pujjr.gps.enumeration.OrderDetailStatus;
import com.pujjr.gps.enumeration.OrderStatus;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.CheckhisService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.base.BaseService;

import tk.mybatis.mapper.entity.Example;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class OrderServiceImpl extends BaseService<OrderService> implements OrderService {

	@Autowired
	ApplyService applyService;

	@Autowired
	GpsOrderMapper gpsOrderMapper;

	@Autowired
	GpsOrderDetailMapper gpsOrderDetailMapper;

	@Autowired
	GpsSupplierMapper gpsSupplierMapper;

	@Autowired
	CheckhisService checkhisService;
	@Autowired
	private GpsApplyMapper gpsApplyMapper;
	@Autowired
	private GpsReceiveMapper gpsReceiveMapper;
	
	

	@Override
	public GpsOrder selectByOrderId(String orderId) {
		// 获取申请单
		Assert.isTrue(StringUtils.isNotBlank(orderId), "订单编号不能为空");
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(orderId);
		Assert.notNull(gpsOrder, "订单编号对应的申请单记录不存在");
		// 获取供应商
		GpsSupplier gpsSupplier = selectSupplierBySupplierId(gpsOrder.getSupplierId());
		// 获取订单明细
		List<GpsOrderDetail> gpsOrderDetailList = selectOrderDetailAndApplyListByOrderId(gpsOrder);
		// 获取审核审批明细
		List<GpsCheckhis> gpsCheckhisList = checkhisService.selectByPubId(orderId);
		// 存入
		gpsOrder.setGpsSupplier(gpsSupplier);
		gpsOrder.setGpsOrderDetailList(gpsOrderDetailList);
		gpsOrder.setGpsCheckhisList(gpsCheckhisList);
		return gpsOrder;
	}

	@Override
	public List<GpsOrderDetail> selectOrderDetailListByOrderId(String orderId) {
		Assert.isTrue(StringUtils.isNotBlank(orderId), "订单编号不能为空");
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectByOrderId(orderId);
		// Assert.isTrue(BaseIterableUtils.isNotEmpty(gpsOrderDetailList), "订单编号对应的订单记录不存在");
		return gpsOrderDetailList;
	}

	@Override
	public GpsSupplier selectSupplierBySupplierId(String supplierId) {
		// 获取联系人
		Assert.isTrue(StringUtils.isNotBlank(supplierId), "供应商编号不能为空");
		GpsSupplier gpsSupplier = gpsSupplierMapper.selectByPrimaryKey(supplierId);
		Assert.notNull(gpsSupplier, "供应商编号对应的供应商记录不存在");
		return gpsSupplier;
	}

	@Override
	public List<GpsSupplier> selectAllSupplier() {
		// 获取联系人
		List<GpsSupplier> gpsSupplier = gpsSupplierMapper.selectAll();
		Assert.notNull(gpsSupplier, "供应商列表获取错误");
		return gpsSupplier;
	}

	@Override
	public PageInfo<GpsOrder> gpsOrderList(OrderSearchParam orderSearchParam, String orderStatus) {
		if (orderSearchParam != null) {
			// 分页
			Page<GpsOrder> page = PageHelper.startPage(orderSearchParam.getPageNum(), orderSearchParam.getPageSize());
			// 查询条件
			Example example = new Example(GpsOrder.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo(orderSearchParam);
			if (StringUtils.isNotBlank(orderStatus)) {
				criteria.orIn("orderStatus", Arrays.asList(StringUtils.split(orderStatus, "|")));
			}
			// 排序
			example.orderBy("orderTime").desc();
			gpsOrderMapper.selectByExample(example);
			return new PageInfo<GpsOrder>(page);
		}
		return null;
	}

	@Override
	public GpsOrder tempSaveGpsOrder(OrderParam orderParam) {
		Assert.notNull(orderParam, "请求数据获取错误");
		logger.info("订单暂存:" + JSON.toJSONString(orderParam));
		GpsOrder gpsOrder = null;
		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(orderParam.getOrderId())) {
			gpsOrder = updateGpsOrder(orderParam, OrderStatus.UNCOMMITTED);
		}else{
			gpsOrder = saveGpsOrder(orderParam, OrderStatus.UNCOMMITTED);
		}
		List<OrderDetailParam> orderDetailList = orderParam.getOrderDetailParamList();
		for (OrderDetailParam gpsOrderDetailParam : orderDetailList) {
			GpsApply currGpsApply = gpsApplyMapper.selectByPrimaryKey(gpsOrderDetailParam.getApplyId());
			currGpsApply.setFreeWireNum(currGpsApply.getFreeWireNum() - gpsOrderDetailParam.getWireNum());
			currGpsApply.setFreeWirelessNum(currGpsApply.getFreeWirelessNum() - gpsOrderDetailParam.getWirelessNum());
			currGpsApply.setUsedWireNum(currGpsApply.getUsedWireNum() + gpsOrderDetailParam.getWireNum());
			currGpsApply.setUsedWirelessNum(currGpsApply.getUsedWirelessNum() + gpsOrderDetailParam.getWirelessNum());
			gpsApplyMapper.updateByPrimaryKeySelective(currGpsApply);
		}
		return gpsOrder;
	}

	@Override
	public GpsOrder submitGpsOrder(OrderParam orderParam) {
		Assert.notNull(orderParam, "请求数据获取错误");
		Assert.isTrue(BaseIterableUtils.isNotEmpty(orderParam.getOrderDetailParamList()), "订单明细列表不能为空");
		logger.info("订单提交:" + JSON.toJSONString(orderParam));
		GpsOrder gpsOrder = null;
		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(orderParam.getOrderId())) {
			gpsOrder = updateGpsOrder(orderParam, OrderStatus.SUBMITTED);
		} else {
			gpsOrder = saveGpsOrder(orderParam, OrderStatus.SUBMITTED);
		}
		// 记录
		checkhisService.submitOrder(gpsOrder);
		/**
		 * 更改：对应申请单空闲有线数量、空闲无线数量
		 */
		List<OrderDetailParam> orderDetailList = orderParam.getOrderDetailParamList();
		for (OrderDetailParam gpsOrderDetailParam : orderDetailList) {
			GpsApply currGpsApply = gpsApplyMapper.selectByPrimaryKey(gpsOrderDetailParam.getApplyId());
			currGpsApply.setFreeWireNum(currGpsApply.getFreeWireNum() - gpsOrderDetailParam.getWireNum());
			currGpsApply.setFreeWirelessNum(currGpsApply.getFreeWirelessNum() - gpsOrderDetailParam.getWirelessNum());
			currGpsApply.setUsedWireNum(currGpsApply.getUsedWireNum() + gpsOrderDetailParam.getWireNum());
			currGpsApply.setUsedWirelessNum(currGpsApply.getUsedWirelessNum() + gpsOrderDetailParam.getWirelessNum());
			gpsApplyMapper.updateByPrimaryKeySelective(currGpsApply);
		}
		return gpsOrder;
	}

	/**
	 * 获得申请单详细列表及列表对应的申请单和联系人
	 * 
	 * @param order
	 * @return
	 */
	private List<GpsOrderDetail> selectOrderDetailAndApplyListByOrderId(GpsOrder order) {
		List<GpsOrderDetail> gpsOrderDetailList = selectOrderDetailListByOrderId(order.getOrderId());
		for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
			GpsApply gpsApply = applyService.selectBaseApplyByApplyId(gpsOrderDetail.getApplyId());
			GpsLinkman gpsLinkman = applyService.selectLinkmanByLinkmanId(gpsApply.getLinkmanId());
			gpsApply.setGpsLinkman(gpsLinkman);
			gpsOrderDetail.setGpsApply(gpsApply);
		}
		return gpsOrderDetailList;
	}

	private GpsOrder saveGpsOrder(OrderParam orderParam, OrderStatus orderStatus) {
		Assert.notNull(orderParam, "请求数据获取错误");
		GpsOrder gpsOrder = new GpsOrder();
		// 申请单参数复制
		BeanUtils.copyProperties(orderParam, gpsOrder);
		// 参数合法性检查
		orderCheck(gpsOrder);
		gpsOrder.setOrderId(BaseUtils.get16UUID());
		gpsOrder.setOrderTime(new Date());
		gpsOrder.setCreateTime(new Date());
		gpsOrder.setOrderStatus(orderStatus.getCode());
		// 保存
		gpsOrderMapper.insert(gpsOrder);
		// 订单明细
		gpsOrder = saveOrderDetail(gpsOrder, orderParam.getOrderDetailParamList());
		gpsOrder.setTotalNum(gpsOrder.getWireNum() + gpsOrder.getWirelessNum());
		// 更新
		gpsOrderMapper.updateByPrimaryKeySelective(gpsOrder);
		logger.info("保存订单:" + JSON.toJSONString(gpsOrder));
		return gpsOrder;
	}

	private GpsOrder updateGpsOrder(OrderParam orderParam, OrderStatus orderStatus) {
		Assert.notNull(orderParam, "请求数据获取错误");
		Assert.isTrue(StringUtils.isNotBlank(orderParam.getOrderId()), "订单编号不能为空");
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(orderParam.getOrderId());
		Assert.notNull(gpsOrder, "订编号对应的订单记录不存在");

		gpsOrder.setSupplierId(orderParam.getSupplierId());
		// 参数合法性检查
		orderCheck(gpsOrder);
		gpsOrder.setOrderStatus(orderStatus.getCode());
		// 订单明细
		gpsOrder = saveOrderDetail(gpsOrder, orderParam.getOrderDetailParamList());
		gpsOrder.setTotalNum(gpsOrder.getWireNum() + gpsOrder.getWirelessNum());

		// 更新
		gpsOrderMapper.updateByPrimaryKeySelective(gpsOrder);
		logger.info("修改订单:" + JSON.toJSONString(gpsOrder));
		return gpsOrder;
	}

	@Override
	public GpsOrder saveOrderDetail(GpsOrder gpsOrder, List<OrderDetailParam> orderDetailParamList) {
		Assert.notNull(gpsOrder, "订编号对应的订单记录不存在");
		// 删除旧明细
		if (StringUtils.isNotBlank(gpsOrder.getOrderId())) {
//			gpsOrderDetailMapper.deleteByOrderId(gpsOrder.getOrderId());
			/**
			 * 备注：订单编号已作为索引字段，可能产生死锁，改为根据主键删除(tom 2018-01-03)。
			 */
			List<GpsOrderDetail> gpsOrderDetail = gpsOrderDetailMapper.selectByOrderId(gpsOrder.getOrderId());
			for (GpsOrderDetail delGpsOrderDetail : gpsOrderDetail) {
				gpsOrderDetailMapper.deleteByPrimaryKey(delGpsOrderDetail);
			}
		}
		
		// 创建新订单明细
		List<GpsOrderDetail> gpsOrderDetailList = new ArrayList<>();
		int wireNum = 0;
		int wirelesslNum = 0;
		for (int i = 0; i < orderDetailParamList.size(); i++) {
			OrderDetailParam gpsOrderDetailParam = orderDetailParamList.get(i);
			GpsOrderDetail gpsOrderDetail = new GpsOrderDetail();
			BeanUtils.copyProperties(gpsOrderDetailParam, gpsOrderDetail);
			Assert.isTrue(StringUtils.isNotBlank(gpsOrderDetail.getApplyId()), "申请单号不能为空");
			// 获取对应申请单
			GpsApply gpsApply = applyService.selectByApplyId((gpsOrderDetail.getApplyId()));
			
			orderDetailCheck(gpsOrderDetail, gpsApply);
			/**
			 * 订单审批通过，插入订单明细
			 */
			// 填值
			gpsOrderDetail.setDetailId(BaseUtils.get16UUID());
			gpsOrderDetail.setOrderId(gpsOrder.getOrderId());
			gpsOrderDetail.setBranchId(gpsApply.getBranchId());
			gpsOrderDetail.setBranchName(gpsApply.getBranchName());
			gpsOrderDetail.setCreateAccountId(gpsOrder.getCreateAccountId());
			gpsOrderDetail.setCreateTime(new Date());
			if(gpsOrder.getOrderStatus().equals(OrderStatus.APPROVE_REJECT.getCode())){
				gpsOrderDetail.setOrderDetailStatus(OrderDetailStatus.HAVA_REJECT.getCode());
			}else{
				gpsOrderDetail.setOrderDetailStatus(OrderDetailStatus.WAIT_SEND.getCode());
			}
			
			gpsOrderDetailMapper.insert(gpsOrderDetail);
			gpsOrderDetailList.add(gpsOrderDetail);
			// 统计
			wireNum = wireNum + gpsOrderDetail.getWireNum();
			wirelesslNum = wirelesslNum + gpsOrderDetail.getWirelessNum();
			
			
			/**
			 * 订单拒绝，回滚申请单中已用有线、无线数量，可用有线、无线数量。
			 */
			String currApplyId = gpsOrderDetail.getApplyId();
			if(gpsOrder.getOrderStatus().equals(OrderStatus.APPROVE_REJECT.getCode())){
				int wireNumInOrderDetail = gpsOrderDetail.getWireNum();
				int wirelessNumInOrderDetail = gpsOrderDetail.getWirelessNum();
				GpsApply currGpsApply = gpsApplyMapper.selectByPrimaryKey(currApplyId);
				//回滚可用有线、无线
				currGpsApply.setFreeWireNum(currGpsApply.getFreeWireNum() + wireNumInOrderDetail);
				currGpsApply.setFreeWirelessNum(currGpsApply.getFreeWirelessNum() + wirelessNumInOrderDetail);
				//回滚已用有线、无线
				currGpsApply.setUsedWireNum(currGpsApply.getUsedWireNum() - wireNumInOrderDetail);
				currGpsApply.setUsedWirelessNum(currGpsApply.getUsedWirelessNum() - wirelessNumInOrderDetail);
				gpsApplyMapper.updateByPrimaryKeySelective(currGpsApply);
			}
		}
		gpsOrder.setWireNum(wireNum);
		gpsOrder.setWirelessNum(wirelesslNum);
		gpsOrder.setTotalNum(wireNum + wirelesslNum);
		// 组合
		gpsOrder.setGpsOrderDetailList(gpsOrderDetailList);
		gpsOrderMapper.updateByPrimaryKeySelective(gpsOrder);
		return gpsOrder;
	}

	/**
	 * @param gpsOrder
	 * @return
	 */
	private void orderCheck(GpsOrder gpsOrder) {
		Assert.isTrue(StringUtils.isNotBlank(gpsOrder.getSupplierId()), "供应商编号不能为空");
		// 获取供应商
		GpsSupplier gpsSupplier = gpsSupplierMapper.selectByPrimaryKey(gpsOrder.getSupplierId());
		Assert.notNull(gpsSupplier, "订单对应的供应商记录不存在");
		gpsOrder.setGpsSupplier(gpsSupplier);
		Assert.isTrue(StringUtils.isNotBlank(gpsOrder.getCreateAccountId()), "创建人不能为空");
	}

	private void orderDetailCheck(GpsOrderDetail gpsOrderDetail, GpsApply gpsApply) {
		String applyId = gpsApply.getApplyId();
		String errorMessage = "申请单号为{0}的订单明细出错,{1}";
		// 申请单相关检查
		Assert.notNull(gpsApply, MessageFormat.format(errorMessage, "申请单编号对应的申请单记录不存在"));
		Assert.isTrue((gpsOrderDetail.getWireNum() != null && gpsOrderDetail.getWireNum() >= 0), MessageFormat.format(errorMessage, applyId, "有线GPS数错误"));
		Assert.isTrue((gpsOrderDetail.getWirelessNum() != null && gpsOrderDetail.getWirelessNum() >= 0), MessageFormat.format(errorMessage, applyId, "无线GPS数错误"));

		/**
		 * 2017-12-27
		 * gps主管审批时，可用数量为0，可能如下两项验证出错，故关闭验证
		 */
		// 不能超过申请单
		/*
		Assert.isTrue((gpsOrderDetail.getWireNum() <= gpsApply.getFreeWireNum()), MessageFormat.format(errorMessage, applyId, "订单有线GPS数不能超过申请单可用数量"));
		Assert.isTrue((gpsOrderDetail.getWirelessNum() <= gpsApply.getFreeWirelessNum()), MessageFormat.format(errorMessage, applyId, "订单无线GPS数不能超过申请单可用数量"));
		*/
		// 相加不为0
		Assert.isTrue((gpsOrderDetail.getWireNum() + gpsOrderDetail.getWirelessNum()) > 0, MessageFormat.format(errorMessage, applyId, "有线与无线GPS数量相加不能为0"));
	}

	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public GpsOrderDetail changeOrderDetailStatus(String detailId) {
		/**
		 * 更新订单明细状态为：已收货
		 * 规则：订单明细相关所有收货信息表记录“已收货”，更改订单明细状态为“已收货”，否则为待收货
		 */
		GpsOrderDetail gpsOrderDetail = gpsOrderDetailMapper.selectByPrimaryKey(detailId);;
		List<GpsReceive> gpsReceiveList = gpsReceiveMapper.selectGpsReceiveListByDetailId(detailId, GpsStatus.WAIT_RECEIVE.getCode());
		if(gpsReceiveList.size() == 0){
			//订单明细下所有待收货GPS已完成签收
			gpsOrderDetail.setOrderDetailStatus(OrderDetailStatus.HAVA_RECEIVE.getCode());
			gpsOrderDetailMapper.updateByPrimaryKeySelective(gpsOrderDetail);
		}
		return gpsOrderDetail;
	}

	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public GpsOrder changeOrderStatus(String orderId) {
		/**
		 * 更新订单状态
		 * 规则：订单下所有订单明细状态为：已收货，更改订单状态为已收货，否则为：待收货
		 * 存在代发货订单明细：待发货
		 * 存在待收货明细：待收货
		 * 订单数量==订单明细该属于该订单号的明细数据之和
		 */
//		String orderId = gpsOrderDetail.getOrderId();
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(orderId);
		boolean isWaitSendByOrderId = false;
		boolean isWaitReceiveByOrderId = false;
		List<GpsOrderDetail> orderDetailbyOrderId = gpsOrderDetailMapper.selectByOrderId(orderId);
		//判定订单下是否存在代发货订单明细
		for (GpsOrderDetail tempGpsOrderDetail : orderDetailbyOrderId) {
			if(tempGpsOrderDetail.getOrderDetailStatus().equals(OrderDetailStatus.WAIT_SEND.getCode())){
				isWaitSendByOrderId = true;
				break;
			}
		}
		for (GpsOrderDetail tempGpsOrderDetail : orderDetailbyOrderId) {
			if(tempGpsOrderDetail.getOrderDetailStatus().equals(OrderDetailStatus.WAIT_RECEIVE.getCode())){
				isWaitReceiveByOrderId = true;
				break;
			}
		}
		if(isWaitSendByOrderId){
			//订单下存在待发货订单明细，订单状态为：待发货
			gpsOrder.setOrderStatus(OrderStatus.WAIT_SEND.getCode());
		}else if(isWaitReceiveByOrderId){
			//订单下存在待收货订单明细，订单状态为：待收货
			gpsOrder.setOrderStatus(OrderStatus.WAIT_RECEIVE.getCode());
		}else{
			//不存在待发、待收订单明细，订单状态：已收货
			gpsOrder.setOrderStatus(OrderStatus.HAVE_RECEIVE.getCode());
		}
		gpsOrderMapper.updateByPrimaryKeySelective(gpsOrder);
		return gpsOrder;
	}

	@Override
	public void rollbackOldSelectedApplyGpsNum(OrderParam orderParam) {
		/**
		 * 查询旧订单明细
		 */
		String orderId = orderParam.getOrderId();
		List<GpsOrderDetail> oldGpsOrderDetailList = gpsOrderDetailMapper.selectByOrderId(orderParam.getOrderId());
		for (GpsOrderDetail gpsOrderDetail : oldGpsOrderDetailList) {
			String applyId = gpsOrderDetail.getApplyId();
			int wireNum = gpsOrderDetail.getWireNum();
			int wirelessNum = gpsOrderDetail.getWirelessNum();
			GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyId);
			gpsApply.setFreeWireNum(gpsApply.getFreeWireNum() + wireNum);
			gpsApply.setFreeWirelessNum(gpsApply.getFreeWirelessNum() + wirelessNum);
			gpsApply.setUsedWireNum(gpsApply.getUsedWireNum() - wireNum);
			gpsApply.setUsedWirelessNum(gpsApply.getUsedWirelessNum() - wirelessNum);
			gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
		}
	}

}
