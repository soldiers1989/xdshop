package com.pujjr.gps.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.exception.CheckFailException;
import com.pujjr.common.utils.BaseIterableUtils;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.AffirmReceiveParam;
import com.pujjr.gps.api.ApplybackDetailParam;
import com.pujjr.gps.api.ApplybackParam;
import com.pujjr.gps.api.ExpressDetailParam;
import com.pujjr.gps.api.ReceiveParam;
import com.pujjr.gps.common.MatchUtils;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsBrandMapper;
import com.pujjr.gps.dal.dao.GpsCheckhisMapper;
import com.pujjr.gps.dal.dao.GpsInstoreHisMapper;
import com.pujjr.gps.dal.dao.GpsLinkmanMapper;
import com.pujjr.gps.dal.dao.GpsOrderDetailMapper;
import com.pujjr.gps.dal.dao.GpsOrderMapper;
import com.pujjr.gps.dal.dao.GpsReceiveMapper;
import com.pujjr.gps.dal.dao.GpsSendMapper;
import com.pujjr.gps.dal.dao.GpsStocktakeMapper;
import com.pujjr.gps.dal.dao.GpsStoreBranchMapper;
import com.pujjr.gps.dal.dao.GpsStorePjMapper;
import com.pujjr.gps.dal.dao.GpsSupplierMapper;
import com.pujjr.gps.dal.domain.ExpressReceipt;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsInstoreBranch;
import com.pujjr.gps.dal.domain.GpsInstoreHis;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.dal.domain.GpsSend;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsStorePj;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.GpsStatus;
import com.pujjr.gps.enumeration.GpsType;
import com.pujjr.gps.enumeration.OrderDetailStatus;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.ReceiveService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class ReceiveServiceImpl extends BaseService<ReceiveService> implements ReceiveService {

	@Autowired
	private OrderService orderService;
	@Autowired
	private GpsReceiveMapper gpsReceiveMapper;
	@Autowired
	private GpsSupplierMapper gpsSupplierMapper;
	@Autowired
	private GpsOrderDetailMapper gpsOrderDetailMapper;
	@Autowired
	private ApplyService applyServiceImpl;
	@Autowired
	private GpsCheckhisMapper gpsCheckhisMapper;
	@Autowired
	private GpsLinkmanMapper gpsLinkmanMapper;
	@Autowired
	private GpsSendMapper gpsSendMapper;
	@Autowired
	private GpsApplyMapper gpsApplyMapper;
	@Autowired
	private GpsStoreBranchMapper gpsStoreBranchMapper;
	@Autowired
	private GpsBrandMapper gpsBrandMapper;
	@Autowired
	private GpsInstoreHisMapper gpsInstoreHisMapper;
	@Autowired
	private GpsOrderMapper gpsOrderMapper;
	@Autowired
	private GpsStocktakeMapper gpsStocktakeMapper;
	@Autowired
	private GpsStorePjMapper gpsStorePjMapper;
	@Autowired
	private StoreService storeService;
	

	@Override
	public GpsOrder selectAllReceiveByOrderId(String orderId) {
		GpsOrder gpsOrder = orderService.selectByOrderId(orderId);
		// 获取订单明细
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrder.getGpsOrderDetailList();
		if (gpsOrder != null) {
			for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
				List<GpsReceive> gpsReceiveList = gpsReceiveMapper.selectByDetailId(gpsOrderDetail.getDetailId());
				gpsOrderDetail.setGpsReceiveList(gpsReceiveList);
			}
		}
		return gpsOrder;
	}

	@Override
	public List<GpsReceive> saveGpsReceive(List<GpsReceive> gpsReceiveList) {
		String detailId = new String();
		String consignee = new String();
		gpsReceiveList = updateGpsReceive(gpsReceiveList, detailId, consignee);
		for (GpsReceive gpsReceive : gpsReceiveList) {
			gpsReceiveMapper.updateByPrimaryKeySelective(gpsReceive);
		}
		return gpsReceiveList;
	}

	@Override
	public List<GpsReceive> submitGpsApply(List<GpsReceive> gpsReceiveList) throws CheckFailException {
		String detailId = new String();
		String consignee = new String();
		List<ApplybackDetailParam> applyBackDetailList = new ArrayList<>();
		logger.info("确认收货提交开始:" + JSON.toJSONString(gpsReceiveList));
		gpsReceiveList = updateGpsReceive(gpsReceiveList, detailId, consignee);
		// 创建出库信息
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		GpsOrderDetail gpsOrderDetail = gpsOrderDetailMapper.selectByPrimaryKey(detailId);
		Assert.notNull(gpsOrderDetail, "未找到对应订单明细");

		for (GpsReceive gpsReceive : gpsReceiveList) {
			GpsSupplier gpsSupplier = MatchUtils.findMatchGpsSupplier(gpsSupplierList, gpsReceive.getSupplierId());
			GpsInstoreBranch gpsInstoreBranch = new GpsInstoreBranch();

			// 变更状态
			if (StringUtils.equals(gpsReceive.getGpsStatus(), GpsStatus.HAVE_RECEIVE.getCode())) {
				// 接收的进行入库记录
				if (StringUtils.equals(gpsSupplier.getIsOutstoreRecord(), GpsSupplier.OUTSTORE_RECORD_IN)) {
					gpsInstoreBranch.setInstoreId(BaseUtils.get16UUID());
					gpsInstoreBranch.setGpsId(gpsReceive.getGpsId());
					gpsInstoreBranch.setBranchId(gpsOrderDetail.getBranchId());
					gpsInstoreBranch.setBranchName(gpsOrderDetail.getBranchName());
					gpsInstoreBranch.setBrandId(gpsReceive.getBrandId());
					gpsInstoreBranch.setGpsCategory(gpsReceive.getGpsCategory());
					gpsInstoreBranch.setCreateAccountId(gpsReceive.getConsignee());
					gpsInstoreBranch.setCreateTime(gpsReceive.getCreateTime());
				}
				logger.info("确认入库:", JSON.toJSONString(gpsReceive));
			} else {
				// 其他的进行拒收
				gpsReceive.setGpsStatus(GpsStatus.REJECT_RECEIVE.getCode());
				// 自动退回
				ApplybackDetailParam applyBackDetailParam = new ApplybackDetailParam();
				applyBackDetailParam.setGpsId(gpsReceive.getGpsId());
				applyBackDetailParam.setGpsStatus(gpsReceive.getGpsStatus());
				applyBackDetailList.add(applyBackDetailParam);
				logger.info("拒收:", JSON.toJSONString(gpsReceive));
			}
			// gpsReceive.setInstoreId(gpsInstoreBranch.getInstoreId());
			gpsReceiveMapper.updateByPrimaryKeySelective(gpsReceive);

		}
		if (BaseIterableUtils.isNotEmpty(applyBackDetailList)) {
			ApplybackParam applyBackParam = new ApplybackParam();
			applyBackParam.setBranchId(gpsOrderDetail.getBranchId());
			applyBackParam.setBranchName(gpsOrderDetail.getBranchName());
			applyBackParam.setCreateAccountId(consignee);
			applyBackParam.setApplybacknNote("自动退回");
			applyBackParam.setApplyBackDetailParamList(applyBackDetailList);
			// TODO 发起退回申请
		}

		return gpsReceiveList;
	}

	private List<GpsReceive> updateGpsReceive(List<GpsReceive> gpsReceiveList, String detailId, String consignee) {
		List<GpsReceive> returnList = new ArrayList<>();
		Assert.isTrue(BaseIterableUtils.isNotEmpty(gpsReceiveList), "确认收货数据不能为空");
		for (GpsReceive gpsReceiveParam : gpsReceiveList) {
			Assert.notNull(gpsReceiveParam, "收货清单信息不能为空");
			Assert.isTrue(StringUtils.isNotBlank(gpsReceiveParam.getReceiveId()), "收货清单编号不能为空");
			Assert.isTrue(StringUtils.isNotBlank(gpsReceiveParam.getConsignee()), "收货人不能为空");
			Assert.isTrue(StringUtils.isNotBlank(gpsReceiveParam.getDetailId()), "明细编号不能为空");
			// 排除不同的detailId
			Assert.isTrue((detailId == null || StringUtils.equals(detailId, gpsReceiveParam.getDetailId())), "明细编号不统一");
			detailId = gpsReceiveParam.getDetailId();
			// 排除不同收货人
			Assert.isTrue((consignee == null || StringUtils.equals(consignee, gpsReceiveParam.getConsignee())), "明细编号不统一");
			consignee = gpsReceiveParam.getConsignee();
			// 验证传的状态
			Assert.isTrue((StringUtils.equals(gpsReceiveParam.getGpsStatus(), GpsStatus.WAIT_RECEIVE.getCode()) || StringUtils.equals(gpsReceiveParam.getGpsStatus(), GpsStatus.HAVE_RECEIVE.getCode())), "GPS收货状态改变错误,无法改变为状态" + gpsReceiveParam.getGpsStatus());
			GpsReceive gpsReceive = gpsReceiveMapper.selectByPrimaryKey(gpsReceiveParam.getReceiveId());
			Assert.notNull(gpsReceive, "无法根据收获清单编号找到收货清单信息");
			gpsReceive.setGpsStatus(gpsReceiveParam.getGpsStatus());
			gpsReceive.setConsignee(gpsReceiveParam.getConsignee());
			gpsReceive.setCreateTime(new Date());
			returnList.add(gpsReceive);
		}
		return returnList;
	}

	@Override
	public GpsApply selectAllReceiveByApplyId(String applyId) {
		GpsApply gpsApply = applyServiceImpl.selectByApplyId(applyId);
		// 获取申请单联系人信息
		String linkmanId = gpsApply == null ? "" : gpsApply.getLinkmanId();
		GpsLinkman gpsLinkman = gpsLinkmanMapper.selectByPrimaryKey(linkmanId);
		gpsApply.setGpsLinkman(gpsLinkman);

		// 获取申请单相关明细
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectByApplyId(applyId);
		gpsApply.setGpsOrderDetailList(gpsOrderDetailList);
		for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
			String detailId = gpsOrderDetail == null ? "" : gpsOrderDetail.getDetailId();
			// 获取订单明细相关发货表信息
			List<GpsSend> gpsSendList = gpsSendMapper.selectByDetailId(detailId);
			gpsOrderDetail.setGpsSendList(gpsSendList);
			// 获取订单明细相关收货表信息
			List<GpsReceive> gpsReceiveList = gpsReceiveMapper.selectByDetailId(detailId);
			gpsOrderDetail.setGpsReceiveList(gpsReceiveList);
		}

		// 获取申请单相关审核审批记录
		List<GpsCheckhis> gpsCheckhisList = gpsCheckhisMapper.selectByPubId(applyId);
		gpsApply.setGpsCheckhisList(gpsCheckhisList);

		//获取申请单盘点信息
		GpsStocktake gpsStocktake = gpsStocktakeMapper.selectGpsStocktakeById(gpsApply.getStocktakeId());
		gpsApply.setGpsStocktake(gpsStocktake);
		return gpsApply;
	}

	@Override
	public List<ExpressReceipt> getExpressListByApplyId(String applyId) {
		// 查询快递单列表(未执行收货操作)
//		List<ExpressReceipt> expressReceiptList = gpsReceiveMapper.selectExpressReceiptByApplyId(applyId, GpsStatus.RECEIVE_WAITING.getCode());
		List<ExpressReceipt> expressReceiptList = gpsReceiveMapper.selectExpressReceiptByApplyId(applyId, "");//查询申请单下所有快递
		// 查询快递单收货明细
		for (ExpressReceipt expressReceipt : expressReceiptList) {
			if (expressReceipt != null) {
				String expressNo = expressReceipt.getExpressNo();
				List<GpsReceive> gpsReceiveList = gpsReceiveMapper.selectGpsRecieveListByExpressNo(expressNo);
				expressReceipt.setGpsReceiveList(gpsReceiveList);
			}
		}
		return expressReceiptList;
	}

//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String receiveGps(AffirmReceiveParam receiveParam){
		// 确认收货的快递单中gps
		List<GpsReceive> gpsReceiveList = receiveParam.getGpsReceiveList();
		String accountId = receiveParam.getAccountId();
		// 订单明细编号：同一批确认收货gps，一定拥有相同订单明细编号
		String detailId = "";
		// 快递单号，同一批确认收货gps一定拥有相同快递单号
		String expressNo = "";
			
		for (int i = 0; i < gpsReceiveList.size(); i++) {
			/**
			 * 更新收货表状态
			 */
			GpsReceive currGpsReceive = gpsReceiveList.get(i);
			String receiveId = currGpsReceive == null ? "" : currGpsReceive.getReceiveId();
			GpsReceive gpsReceiveUpd = gpsReceiveMapper.selectByPrimaryKey(receiveId);
			if(gpsReceiveUpd == null)
				continue;
			if (i == 0) {
				expressNo = currGpsReceive.getExpressNo();
				detailId = gpsReceiveUpd.getDetailId();
			}
			//GPS收货操作人
			gpsReceiveUpd.setConsignee(receiveParam.getAccountId());
			gpsReceiveUpd.setGpsStatus(GpsStatus.HAVE_RECEIVE.getCode());
			gpsReceiveUpd.setReceiveTime(Calendar.getInstance().getTime());
			gpsReceiveMapper.updateByPrimaryKeySelective(gpsReceiveUpd);

			/**
			 * gps入库
			 */
			GpsStoreBranch newGps = new GpsStoreBranch();
			String storeId = BaseUtils.get16UUID();
			newGps.setStoreId(storeId);
			newGps.setGpsId(currGpsReceive.getGpsId());
			// 经销商编码、名称查询
			GpsApply apply = gpsApplyMapper.selectByPrimaryKey(gpsReceiveUpd.getApplyId());
			newGps.setBranchId(apply.getBranchId());
			newGps.setBranchName(apply.getBranchName());
			newGps.setBrandId(gpsReceiveUpd.getBrandId());
			newGps.setGpsCategory(gpsReceiveUpd.getGpsCategory());
			newGps.setGpsStatus(GpsStatus.STORE_FREE.getCode());
			newGps.setSupplierId(gpsReceiveUpd.getSupplierId());
			newGps.setApplyChannel("");
			newGps.setApplyId(gpsReceiveUpd.getApplyId());
			GpsStoreBranch existGpsStoreBranch = gpsStoreBranchMapper.selectByGpsId(newGps.getGpsId());
			Assert.isTrue(existGpsStoreBranch == null, "GPS编码【"+newGps.getGpsId()+"】在经销商库存已存在");
			gpsStoreBranchMapper.insertSelective(newGps);

			/**
			 * 记录入库记录
			 */
			String instoreHisId = BaseUtils.get16UUID();
			GpsInstoreHis instoreHis = new GpsInstoreHis();
			instoreHis.setInstoreHisId(instoreHisId);
			instoreHis.setCreateTime(Calendar.getInstance().getTime());
			instoreHis.setCreateAccountId(accountId);
			instoreHis.setReceiveId(receiveId);
			instoreHis.setStoreId(storeId);
			gpsInstoreHisMapper.insertSelective(instoreHis);
			/**
			 * 潽金库存出库：如果gps供应商为潽金，经销商收货后，执行潽金库存出库（删除潽金库存）
			 */
//			GpsReceive gpsReceiveInDb = gpsReceiveMapper.selectByGpsId(currGpsReceive.getGpsId());
			if(gpsReceiveUpd.getSupplierId().equals("000")){
				GpsStorePj gpsStorePj = new GpsStorePj();
				gpsStorePj.setGpsId(currGpsReceive.getGpsId());
				storeService.delPjStore(gpsStorePj);
			}
		}
		
		/**
		 * 验证前端数据与后端数据一致性
		 */
		List<GpsReceive> gpsReceiveListInDb = gpsReceiveMapper.selectGpsRecieveListByExpressNo(expressNo);
		for (GpsReceive gpsReceiveInDb : gpsReceiveListInDb) {
			boolean isCurrExist = false;
			for (GpsReceive gpsReceiveFromClient : gpsReceiveList) {
				if(gpsReceiveInDb.getGpsId().equals(gpsReceiveFromClient.getGpsId())){
					isCurrExist = true;
					break;
				}
			}
			Assert.isTrue(isCurrExist, "页面缺少新增GPS【"+gpsReceiveInDb.getGpsId()+"】,请点击【返回】后重新进入此页面");
		}
		for (GpsReceive gpsReceiveFromClient : gpsReceiveList) {
			boolean isCurrExist = false;
			for (GpsReceive gpsReceiveInDb : gpsReceiveListInDb) {
				if(gpsReceiveFromClient.getGpsId().equals(gpsReceiveInDb.getGpsId())){
					isCurrExist = true;
					break;
				}
			}
			Assert.isTrue(isCurrExist, "页面存在已删除GPS【"+gpsReceiveFromClient.getGpsId()+"】,请点击【返回】后重新进入此页面");
		}
		
		/*
		for (int i = 0; i < gpsReceiveList.size(); i++) {
			*//**
			 * 更新收货表状态
			 *//*
			GpsReceive currGpsReceive = gpsReceiveList.get(i);
			String receiveId = currGpsReceive == null ? "" : currGpsReceive.getReceiveId();
			GpsReceive gpsReceiveUpd = gpsReceiveMapper.selectByPrimaryKey(receiveId);
			System.out.println(gpsReceiveUpd);
		}
		
		List<GpsReceive> gpsReceiveList2 = gpsReceiveMapper.selectGpsReceiveListByDetailId(detailId, GpsStatus.WAIT_RECEIVE.getCode());
		System.out.println(gpsReceiveList2);*/
		return detailId;
	}
	
	@Override
	public void commitReceive(AffirmReceiveParam receiveParam) {
		String detailId = this.receiveGps(receiveParam);
//		更新订单明细状态
		GpsOrderDetail gpsOrderDetail = orderService.changeOrderDetailStatus(detailId);
//		更新订单状态
		if(gpsOrderDetail != null){
			GpsOrder gpsOrder = orderService.changeOrderStatus(gpsOrderDetail.getOrderId());
		}
//		更新申请单状态
		if(gpsOrderDetail != null){
			applyServiceImpl.changeGpsApplyStatus(gpsOrderDetail.getApplyId());
		}
		
		logger.info("状态更新完成");
	}

	public GpsReceive saveReceive(ReceiveParam receiveParam) {
		Assert.notNull(receiveParam, "请求对象不能为空");
		
		GpsReceive gpsReceive = gpsReceiveMapper.selectByGpsId(receiveParam.getGpsId());
		if (StringUtils.isNotBlank(receiveParam.getReceiveId())) {
			GpsReceive oldGpsReceive = gpsReceiveMapper.selectByPrimaryKey(receiveParam.getReceiveId());
			if(gpsReceive != null){
				Assert.isTrue(oldGpsReceive.getGpsId().equals(receiveParam.getGpsId()), "操作失败！GPS编码 "+receiveParam.getGpsId()+" 已存在");
			}
			return updateReceive(receiveParam);
		}
		Assert.isTrue(gpsReceive == null, "操作失败！GPS编码 "+receiveParam.getGpsId()+" 已存在");
		return addReceive(receiveParam);
	}

	@Override
	public GpsReceive addReceive(ReceiveParam receiveParam) {
		Assert.notNull(receiveParam, "请求对象不能为空");
		checkReceive(receiveParam);
		GpsReceive gpsReceive = new GpsReceive();
		BeanUtils.copyProperties(receiveParam, gpsReceive);
		gpsReceive.setReceiveId(BaseUtils.get16UUID());
		gpsReceive.setCreateTime(new Date());
		gpsReceive.setGpsStatus(GpsStatus.WAIT_RECEIVE.getCode());
		gpsReceiveMapper.insert(gpsReceive);
		
		/**
		 * 2018-03-27 tom
		 * 添加收货表GPS后，如果是潽金库存发货，锁定潽金库存对饮GPS
		 * 		
		 */
		String gpsId = receiveParam.getGpsId();
		String detailId = receiveParam.getDetailId();
		GpsOrderDetail gpsOrderDetail = gpsOrderDetailMapper.selectByPrimaryKey(detailId);
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(gpsOrderDetail.getOrderId());
		if("000".equals(gpsOrder.getSupplierId())){
			GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
			gpsStorePj.setGpsStatus(GpsStatus.STORE_LOCK.getCode());
			gpsStorePjMapper.updateByPrimaryKeySelective(gpsStorePj);
		}
		return gpsReceive;
	}

	@Override
	public GpsReceive updateReceive(ReceiveParam receiveParam) {
		Assert.notNull(receiveParam, "请求对象不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getReceiveId()), "收货清单编号不能为空");
		GpsReceive gpsReceive = gpsReceiveMapper.selectByPrimaryKey(receiveParam.getReceiveId());
		Assert.notNull(gpsReceive, "未找到对应收货清单:" + receiveParam.getReceiveId());
		Assert.isTrue(!StringUtils.equals(GpsStatus.HAVE_RECEIVE.getCode(), gpsReceive.getGpsStatus()), "不能对已收货的收货清单进行操作");
		checkReceive(receiveParam);
		BeanUtils.copyProperties(receiveParam, gpsReceive);
		gpsReceive.setGpsStatus(GpsStatus.WAIT_RECEIVE.getCode());
		gpsReceiveMapper.updateByPrimaryKeySelective(gpsReceive);
		return gpsReceive;
	}

	@Override
	public int deleteReceive(String receiveId, String operUserId) {
		Assert.isTrue(StringUtils.isNotBlank(receiveId), "收货清单编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(operUserId), "操作用户编号不能为空");
		GpsReceive gpsReceive = gpsReceiveMapper.selectByPrimaryKey(receiveId);
		Assert.notNull(gpsReceive, "未找到对应收货清单:" + receiveId);
		GpsOrderDetail orderDetail = gpsOrderDetailMapper.selectByPrimaryKey(gpsReceive.getDetailId());
		Assert.notNull(orderDetail, "未找到收获清单对应订单明细:" + gpsReceive.getDetailId());
		Assert.isTrue(StringUtils.equals(OrderDetailStatus.WAIT_RECEIVE.getCode(), orderDetail.getOrderDetailStatus()), "只能对待收货的订单明细下的收货清单进行操作");
		
		/**
		 * 删除收货表
		 */
		int delNum = gpsReceiveMapper.delete(gpsReceive);
		
		/**
		 * 2018-03-27 tom
		 * 如果潽金库存发货：
		 * 		变更已删除收货表GPS在潽金库存中状态：锁定-->>>空闲
		 */
		String orderId = orderDetail == null ? "" : orderDetail.getOrderId();
		GpsOrder gpsOrder = gpsOrderMapper.selectByPrimaryKey(orderId);
		if(gpsOrder != null){
			if("000".equals(gpsOrder.getSupplierId())){
				String gpsId = gpsReceive.getGpsId();
				GpsStorePj gpsStorePj = gpsStorePjMapper.selectStorePjByGpsId(gpsId);
				if(gpsStorePj != null){
					gpsStorePj.setGpsStatus(GpsStatus.STORE_FREE.getCode());
					gpsStorePjMapper.updateByPrimaryKeySelective(gpsStorePj);
				}
			}
		}
		return delNum;
	}

	/**
	 * 退货申请检查
	 * 
	 * @param receiveParam
	 */
	private void checkReceive(ReceiveParam receiveParam) {
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getBrandId()), "品牌编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getGpsId()), "GPS编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getGpsId()), "品牌编号不能为空");
		Assert.isTrue(GpsType.contains(receiveParam.getGpsCategory()), "未知GPS类型" + receiveParam.getGpsCategory());
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getExpressCompany()), "快递公司不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getExpressNo()), "快递单号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getSupplierId()), "供应商编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getApplyId()), "申请单号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getOperUserId()), "操作用户编号不能为空");
		// 高级限制
		Assert.isTrue(StringUtils.isNotBlank(receiveParam.getDetailId()), "订单明细编号不能为空");
		GpsOrderDetail orderDetail = gpsOrderDetailMapper.selectByPrimaryKey(receiveParam.getDetailId());
		Assert.notNull(orderDetail, "未找到收获清单对应订单明细:" + receiveParam.getDetailId());
		Assert.isTrue(StringUtils.equals(OrderDetailStatus.WAIT_RECEIVE.getCode(), orderDetail.getOrderDetailStatus()), "只能对待收货的订单明细下的收货清单进行操作");
		Assert.isTrue(StringUtils.equals(receiveParam.getApplyId(), orderDetail.getApplyId()), "申请单编号与订单明细申请单编号不统一");
		GpsSupplier gpsSupplier = gpsSupplierMapper.selectByPrimaryKey(receiveParam.getSupplierId());
		Assert.notNull(gpsSupplier, "未找到对应供应商:" + receiveParam.getSupplierId());
		GpsBrand gpsBrand = gpsBrandMapper.selectByPrimaryKey(receiveParam.getBrandId());
		Assert.notNull(gpsBrand, "未找到对应品牌:" + receiveParam.getBrandId());
	}

	public PageInfo<GpsReceive> getGpsReceiveByExpressNo(ExpressDetailParam expressDetailParam,String expressNo) {
		//暂不启用分页查询
//		PageHelper.startPage(expressDetailParam.getPageNum(), expressDetailParam.getPageSize());
		
		List<GpsReceive> gpsReceiveList = gpsReceiveMapper.selectGpsRecieveListByExpressNo(expressNo);
		PageInfo<GpsReceive> pageInfo = new PageInfo<GpsReceive>(gpsReceiveList);
		return pageInfo;
	}
}
