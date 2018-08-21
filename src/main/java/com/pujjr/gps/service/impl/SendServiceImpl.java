package com.pujjr.gps.service.impl;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.exception.CheckFailException;
import com.pujjr.common.utils.BaseIterableUtils;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.common.MatchUtils;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsBrandMapper;
import com.pujjr.gps.dal.dao.GpsOrderDetailMapper;
import com.pujjr.gps.dal.dao.GpsOrderMapper;
import com.pujjr.gps.dal.dao.GpsOutstorePjMapper;
import com.pujjr.gps.dal.dao.GpsReceiveMapper;
import com.pujjr.gps.dal.dao.GpsSendMapper;
import com.pujjr.gps.dal.dao.GpsStoreBranchMapper;
import com.pujjr.gps.dal.dao.GpsStorePjMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsOutstorePj;
import com.pujjr.gps.dal.domain.GpsReceive;
import com.pujjr.gps.dal.domain.GpsSend;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.ApplyStatus;
import com.pujjr.gps.enumeration.GpsStatus;
import com.pujjr.gps.enumeration.GpsType;
import com.pujjr.gps.enumeration.OrderDetailStatus;
import com.pujjr.gps.enumeration.OrderStatus;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.ExcelService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.SendService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.vo.GpsStorePjVo;
import com.pujjr.gps.vo.SendFeedbackVO;
import com.pujjr.gps.vo.SendGpsDetailVO;

import tk.mybatis.mapper.entity.Example;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class SendServiceImpl extends BaseService<SendService> implements SendService {

	@Autowired
	OrderService orderService;

	@Autowired
	ApplyService applyService;

	@Autowired
	ExcelService excelService;

	@Autowired
	GpsOrderDetailMapper gpsOrderDetailMapper;

	@Autowired
	GpsOutstorePjMapper gpsOutstorePjMapper;

	@Autowired
	GpsSendMapper gpsSendMapper;

	@Autowired
	GpsReceiveMapper gpsReceiveMapper;

	@Autowired
	GpsBrandMapper gpsBrandMapper;
	@Autowired
	GpsOrderMapper gpsOrderMapper;
	@Autowired
	GpsStoreBranchMapper gpsStoreBranchMapper;
	@Autowired
	private GpsApplyMapper gpsApplyMapper;
	@Autowired
	private GpsStorePjMapper gpsStorePjMapper;
	@Autowired
	private StoreService storeService;

	@Override
	public GpsOrder selectAllSendByOrderId(String orderId) {
		GpsOrder gpsOrder = orderService.selectByOrderId(orderId);
		// 获取订单明细
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrder.getGpsOrderDetailList();
		if (gpsOrder != null) {
			for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
				List<GpsSend> gpsSendList = gpsSendMapper.selectByDetailId(gpsOrderDetail.getDetailId());
				gpsOrderDetail.setGpsSendList(gpsSendList);
				int sumWireNum = 0;
				int sumWirelessNum = 0;
				// 创建GPS收发货清单
				for (GpsSend gpsSend : gpsSendList) {
					if (StringUtils.equals(gpsSend.getGpsCategory(), GpsType.WIRE.name())) {
						sumWireNum++;
					} else {
						sumWirelessNum++;
					}
				}
				gpsOrderDetail.setSumWireNum(sumWireNum);
				gpsOrderDetail.setSumWirelessNum(sumWirelessNum);
			}
		}
		return gpsOrder;
	}

	@Override
	public List<GpsSend> saveGpsSendListByExcelFile(InputStream inputStream, String consigner) throws Exception {
		List<GpsSend> gpsSendList = new ArrayList<>();
		Assert.notNull(inputStream, "文件流获取错误");
		SendFeedbackVO sendFeedback = excelService.importSendFeedback(inputStream);
		Assert.notNull(sendFeedback, "上传文件数据不能为空");
		Assert.isTrue(StringUtils.isNotBlank(consigner), "发货人不能为空");
		Assert.isTrue(StringUtils.isNotBlank(sendFeedback.getOrderId()), "订单号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(sendFeedback.getSupplyName()), "供应商名称不能为空");
		// 创建数据
		GpsOrder gpsOrder = orderService.selectByOrderId(sendFeedback.getOrderId());
		GpsSupplier supplier = gpsOrder.getGpsSupplier();
		Assert.isTrue(StringUtils.equals(supplier.getSupplierName(), sendFeedback.getSupplyName()), "导入供应商名称与订单供应商名称不匹配。应："+supplier.getSupplierName()+"实："+sendFeedback.getSupplyName());
		// excel中读取数据
		List<SendGpsDetailVO> gpsDetailList = sendFeedback.getSendGpsDetailList();
		// 校验excel中GPS编号是否重复
		for (int i = 0; i < gpsDetailList.size(); i++) {
			SendGpsDetailVO gpsLoop = gpsDetailList.get(i);
			for (int j = 0; j < gpsDetailList.size(); j++) {
				SendGpsDetailVO gpsTarget = gpsDetailList.get(j);
				if (i != j) {
					Assert.isTrue(!gpsLoop.getGpsId().equals(gpsTarget.getGpsId()), "导入失败！所导入数据中存在重复GPS编码：" + gpsTarget.getGpsId());
				}
			}
		}
		// 查询所有经销商库存
		List<GpsStoreBranch> allGpsStoreBranch = gpsStoreBranchMapper.selectAll();
		//校验gps编号是否已存在于经销商库存表
		for (SendGpsDetailVO gpsDetail : gpsDetailList) {
			boolean isExist = false;
			for (GpsStoreBranch gpsStoreBranch : allGpsStoreBranch) {
				if (gpsDetail.getGpsId().equals(gpsStoreBranch.getGpsId())) {
					isExist = true;
					break;
				}
			}
			Assert.isTrue(!isExist, "导入失败！GPS编号【" + gpsDetail.getGpsId() + "】在经销商库存表已存在！");
		}
		
		List<GpsBrand> gpsBrandList = selectAllBrand();
		Assert.isTrue(BaseIterableUtils.isNotEmpty(gpsDetailList), "导入的GPS数据为空");
		Assert.isTrue(BaseIterableUtils.isNotEmpty(gpsBrandList), "品牌数据列表为空");
		
		
		/**
		 * 更新潽金库存锁定状态
		 * 如果订单gps来源为：潽金库存，上传发货信息后，解锁之前导入所有gps，并锁定新导入gps
		 */
		if(gpsOrder.getSupplierId().equals("000")){
			/**
			 * 解锁潽金库存
			 */
			List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectByOrderId(gpsOrder.getOrderId());
			for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
				String detailId = gpsOrderDetail.getDetailId();
				List<GpsSend> tempSendList = gpsSendMapper.selectByDetailId(detailId);
				for (GpsSend gpsSend : tempSendList) {
					String tempGpsId = gpsSend.getGpsId();
					//解锁潽金库存
					storeService.unLockStorePj(tempGpsId);
				}
			}
			
			/**
			 * 校验导入gps在潽金库存状态，只有空闲状态gps允许导入
			 */
			for (SendGpsDetailVO gpsDetail : gpsDetailList) {
				String tempGpsId = gpsDetail.getGpsId();
				StorePjParam storePjParam = new StorePjParam();
				List<GpsStorePjVo> tempGpsStorePjList = storeService.getAllPjStore(storePjParam);
				for (GpsStorePjVo gpsStorePjVo : tempGpsStorePjList) {
					if(tempGpsId.equals(gpsStorePjVo.getGpsId())){
						Assert.isTrue(gpsStorePjVo.getGpsStatus().equals(GpsStatus.STORE_FREE.getCode()), "GPS编码【"+tempGpsId+"】在潽金库存中状态为："+gpsStorePjVo.getGpsStatusName()+",只能导入【空闲】状态的潽金库存GPS！");
						Assert.isTrue(gpsStorePjVo.getBrandName().equals(gpsDetail.getBrandName()), "GPS编码【"+tempGpsId+"】品牌错误，应："+gpsStorePjVo.getBrandName()+" 实："+gpsDetail.getBrandName());
						//gpsDetail.getGpsCategory():excel导入的“有线设备”、“无线设备”
						Assert.isTrue(gpsStorePjVo.getGpsCategoryName().equals(gpsDetail.getGpsCategory()),"GPS编码【"+tempGpsId+"】设备类型错误，应："+gpsStorePjVo.getGpsCategoryName()+" 实："+gpsDetail.getGpsCategory());
					}
				}
			}
			
			/**
			 * 锁定潽金库存锁定状态
			 */
			for (SendGpsDetailVO gpsDetail : gpsDetailList) {
				String tempGpsId = gpsDetail.getGpsId();
				//锁定潽金库存
				storeService.lockStorePj(tempGpsId);
			}
		}
		
		/**
		 * 清除旧数据
		 */
		Set<String> deleteSet = new HashSet<>();
		// 填装发货清单数据
		for (SendGpsDetailVO sendGpsDetail : gpsDetailList) {
			GpsSend gpsSend = getGpsSend(gpsOrder, sendGpsDetail, gpsBrandList, consigner);
			// 清除旧数据
			String detailId = gpsSend.getDetailId();
			// 如果没删过就删掉
			if (!deleteSet.contains(detailId)) {
				// TODO 如果没传上次传入的, 则无法删除旧数据
				gpsSendMapper.deleteByDetailId(detailId);
				deleteSet.add(detailId);
			}
			gpsSendMapper.insert(gpsSend);
			gpsSendList.add(gpsSend);
		}
		
		/**
		 * 校验快递单号是否已存在于发货表（与之前已发货订单的快递单号对比）
		 */
		List<GpsSend> allGpsSendList = gpsSendMapper.selectAllSendByIsSend(1);
		for (SendGpsDetailVO gpsDetail : gpsDetailList) {
			for (GpsSend gpsSend : allGpsSendList) {
				Assert.isTrue(!gpsSend.getExpressNo().equals(gpsDetail.getExpressNo()), "导入失败！快递单号【" + gpsDetail.getExpressNo() + "】已存在！");
			}
		}
		return gpsSendList;
	}

	/**
	 * 确认发货
	 */
	@Override
	public GpsOrder submitSend(String orderId) {
		Assert.isTrue(StringUtils.isNotBlank(orderId), "订单号不能为空");
		// 创建数据
		GpsOrder gpsOrder = orderService.selectByOrderId(orderId);
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrder.getGpsOrderDetailList();
		GpsSupplier gpsSupplier = gpsOrder.getGpsSupplier();
		logger.info("订单号" + orderId + "提交发货");
		for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
			List<GpsSend> gpsSendList = gpsSendMapper.selectByDetailId(gpsOrderDetail.getDetailId());
			String branchName = gpsOrderDetail.getBranchName();
			Assert.isTrue(BaseIterableUtils.isNotEmpty(gpsSendList), branchName + "GPS发货列表为空");
			int sumWireNum = 0;
			int sumWirelessNum = 0;
			// 创建GPS收发货清单
			for (GpsSend gpsSend : gpsSendList) {
				if (StringUtils.equals(gpsSend.getGpsCategory(), GpsType.WIRE.name())) {
					sumWireNum++;
				} else {
					sumWirelessNum++;
				}
				// 发货记录状态
				gpsSend.setIsSend(GpsSend.SEND_YES);
				gpsSend.setSendTime(new Date());
				// 出库记录
				if (StringUtils.equals(gpsSupplier.getIsOutstoreRecord(), GpsSupplier.OUTSTORE_RECORD_OUT)) {
					GpsOutstorePj outstore = new GpsOutstorePj();
					outstore.setOutstoreId(BaseUtils.get16UUID());
					outstore.setGpsId(gpsSend.getGpsId());
					outstore.setCreateAccountId(gpsSend.getConsigner());
					outstore.setCreateTime(gpsSend.getSendTime());
					gpsOutstorePjMapper.insert(outstore);
				}
				gpsSendMapper.updateByPrimaryKeySelective(gpsSend);
				// 创建GPS收货清单
				GpsReceive gpsReceive = new GpsReceive();
				BeanUtils.copyProperties(gpsSend, gpsReceive);
				gpsReceive.setReceiveId(BaseUtils.get16UUID());
				gpsReceive.setGpsStatus(GpsStatus.WAIT_RECEIVE.getCode());
				gpsReceiveMapper.insert(gpsReceive);

			}
			gpsOrderDetail.setGpsSendList(gpsSendList);
			// gps数统计
			Assert.isTrue(gpsOrderDetail.getWireNum() == sumWireNum, branchName + "有线GPS总数与订单明细不匹配");
			Assert.isTrue(gpsOrderDetail.getWirelessNum() == sumWirelessNum, branchName + "无线GPS总数与订单明细不匹配");
			// 订单明细状态:待收货
			gpsOrderDetail.setOrderDetailStatus(OrderDetailStatus.WAIT_RECEIVE.getCode());
			gpsOrderDetailMapper.updateByPrimaryKeySelective(gpsOrderDetail);
			// 申请单待收货订单
			applyService.updateWaitReceiveOrderNum(gpsOrderDetail.getApplyId(), 1);

			// 订单状态改为：待收货
			gpsOrder.setOrderStatus(OrderStatus.WAIT_RECEIVE.getCode());
			gpsOrderMapper.updateByPrimaryKeySelective(gpsOrder);
			
			//修改订单明细对应申请单状态为：待收货
			String applyId = gpsOrderDetail.getApplyId();
			GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyId);
			gpsApply.setApplyStatus(ApplyStatus.WAIT_RECEIVE.getCode());
			gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
		}
		return gpsOrder;
	}

	@Override
	public PageInfo<GpsSend> gpsSendListByDetailId(String detailId, int pageNum, int pageSize) {
		Assert.isTrue(StringUtils.isNotBlank(detailId), "明细编号不能为空");
		Page<GpsSend> page = PageHelper.startPage(pageNum, pageSize);
		// 查询条件
		Example example = new Example(GpsSend.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("detailId", detailId);
		// 排序
//		example.orderBy("expressCompany");
		example.orderBy("gpsId");
		gpsSendMapper.selectByExample(example);
		PageInfo<GpsSend> pageInfo = new PageInfo<GpsSend>(page);
		return pageInfo;
	}

	/**
	 * 获取品牌列表
	 * 
	 * @return
	 */
	@Override
	public List<GpsBrand> selectAllBrand() {
		// 获取联系人
		List<GpsBrand> gpsBrand = gpsBrandMapper.selectAll();
		Assert.notNull(gpsBrand, "品牌列表获取错误");
		return gpsBrand;
	}

	/**
	 * 填装发货数据
	 * 
	 * @param gpsOrder
	 * @param sendGpsDetail
	 * @param gpsBrandList
	 * @param consigner
	 * @return
	 * @throws CheckFailException
	 */
	private GpsSend getGpsSend(GpsOrder gpsOrder, SendGpsDetailVO sendGpsDetail, List<GpsBrand> gpsBrandList, String consigner) throws CheckFailException {
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrder.getGpsOrderDetailList();
		GpsOrderDetail gpsOrderDetail = MatchUtils.findMatchGpsOrderDetail(gpsOrderDetailList, sendGpsDetail.getDetailId());
		// 检查
		sendCheck(sendGpsDetail, gpsOrderDetail);
		// 填值
		GpsSend gpsSend = new GpsSend();
		gpsSend.setSendId(BaseUtils.get16UUID());
		gpsSend.setDetailId(gpsOrderDetail.getDetailId());
		gpsSend.setGpsId(sendGpsDetail.getGpsId());
		gpsSend.setGpsCategory(MatchUtils.findMatchGpsCategoryCode(sendGpsDetail.getGpsCategory()));
		gpsSend.setCreateTime(new Date());
		gpsSend.setConsigner(consigner);
		gpsSend.setIsSend(GpsSend.SEND_NOT);
		gpsSend.setExpressCompany(sendGpsDetail.getExpressCompany());
		gpsSend.setExpressNo(sendGpsDetail.getExpressNo());
		gpsSend.setSupplierId(gpsOrder.getSupplierId());
		gpsSend.setBrandId(MatchUtils.findMatchGpsBrandName(gpsBrandList, sendGpsDetail.getBrandName()));
		gpsSend.setApplyId(gpsOrderDetail.getApplyId());

		return gpsSend;

	}

	/**
	 * @param gpsApply
	 */
	private void sendCheck(SendGpsDetailVO sendGpsDetail, GpsOrderDetail gpsOrderDetail) {
		String message = MessageFormat.format("订单明细编号为[{0}],GPS编号为[{1}]的数据,", sendGpsDetail.getDetailId(), sendGpsDetail.getGpsId());
		Assert.isTrue(StringUtils.equals(sendGpsDetail.getBranchName(), gpsOrderDetail.getBranchName()), "经销商名称不匹配,订单明细编码："+sendGpsDetail.getDetailId()+"|发货明细中："+sendGpsDetail.getBranchName()+"|订单明细中："+gpsOrderDetail.getBranchName());
		Assert.isTrue(StringUtils.isNotBlank(sendGpsDetail.getGpsId()), message + "设备编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(sendGpsDetail.getExpressCompany()), message + "快递公司不能为空");
		Assert.isTrue(StringUtils.isNotBlank(sendGpsDetail.getExpressNo()), message + "快递单号不能为空");

		Assert.isTrue(StringUtils.length(sendGpsDetail.getGpsId()) < GpsSend.GPSID_LENGTH, "GPS设备编号长度过长");
		Assert.isTrue(StringUtils.length(sendGpsDetail.getExpressCompany()) < GpsSend.EXPRESS_COMPANY_LENGTH, "快递公司名称长度过长");
		Assert.isTrue(StringUtils.length(sendGpsDetail.getExpressNo()) < GpsSend.EXPRESS_NO_LENGTH, "快递单号长度过长");

		// TODO 增加设备数超过订单数的检查
	}

}
