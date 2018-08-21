package com.pujjr.gps.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.ApplybackDetailParam;
import com.pujjr.gps.api.ApplybackParam;
import com.pujjr.gps.api.ApplybackSearchParam;
import com.pujjr.gps.dal.dao.GpsApplybackDetailMapper;
import com.pujjr.gps.dal.dao.GpsApplybackDetailhisMapper;
import com.pujjr.gps.dal.dao.GpsApplybackMapper;
import com.pujjr.gps.dal.dao.GpsBrandMapper;
import com.pujjr.gps.dal.dao.GpsStoreBranchMapper;
import com.pujjr.gps.dal.dao.GpsSupplierMapper;
import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;
import com.pujjr.gps.dal.domain.GpsApplybackDetailhis;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.dal.domain.GpsStorePj;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.ApplyStatus;
import com.pujjr.gps.enumeration.ApplybackDetailStatus;
import com.pujjr.gps.enumeration.ApplybackStatus;
import com.pujjr.gps.enumeration.GpsStatus;
import com.pujjr.gps.service.ApplybackService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.vo.ApplyBackGpsVo;
import com.pujjr.gps.vo.GpsApplybackVo;
import com.pujjr.gps.vo.GpsStoreBranchVo;

import tk.mybatis.mapper.entity.Example;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class ApplybackServiceImpl extends BaseService<ApplybackService> implements ApplybackService {

	@Autowired
	private GpsApplybackMapper gpsApplybackMapper;
	@Autowired
	private GpsApplybackDetailMapper gpsApplybackDetailMapper;
	@Autowired
	private GpsApplybackDetailhisMapper gpsApplybackDetailhisMapper;
	@Autowired
	private GpsStoreBranchMapper gpsStoreBranchMapper;
	@Autowired
	private StoreService storeService;
	@Autowired
	private GpsBrandMapper gpsBrandMapper;
	@Autowired
	private GpsSupplierMapper gpsSupplierMapper;

	@Override
	public PageInfo<GpsApplybackVo> gpsApplyList(ApplybackSearchParam applybackSearchParam, String applyStatus) {
		// 分页
		Page<GpsApplyback> page = PageHelper.startPage(applybackSearchParam.getPageNum(), applybackSearchParam.getPageSize());
		// 查询条件
		Example example = new Example(GpsApplyback.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo(applybackSearchParam);
		if (StringUtils.isNotBlank(applyStatus)) {
			criteria.andIn("applybackStatus", Arrays.asList(StringUtils.split(applyStatus, "|")));
		}
		// 排序
		// example.orderBy("applyTime");
		gpsApplybackMapper.selectByExample(example);
		PageInfo<GpsApplyback> pageInfo = new PageInfo<GpsApplyback>(page);
		PageInfo<GpsApplybackVo> pageInfoVo = new PageInfo<GpsApplybackVo>();
		BeanUtils.copyProperties(pageInfo, pageInfoVo);
		/**
		 * 获取申请单状态名，组装vo对象
		 */
		List<GpsApplyback> list = pageInfo.getList();
		List<GpsApplybackVo> voList = new ArrayList<GpsApplybackVo>();
		for (GpsApplyback gpsApplyback : list) {
			GpsApplybackVo vo = new GpsApplybackVo();
			BeanUtils.copyProperties(gpsApplyback, vo);
			ApplybackStatus status = ApplybackStatus.fromValue(vo.getApplybackStatus());
			if (status != null) {
				vo.setApplybackStatusName(status.getRemark());
			}
			voList.add(vo);
		}
		pageInfoVo.setList(voList);
		return pageInfoVo;
	}

	@Override
	public GpsApplyback tempSaveGpsApplyback(ApplybackParam applyBackParam) {
		Assert.notNull(applyBackParam, "请求数据获取错误");
		logger.info("退回申请暂存:" + JSON.toJSONString(applyBackParam));
		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(applyBackParam.getApplybackId())) {
			return updateGpsApplyback(applyBackParam, ApplybackStatus.UNCOMMITTED);
		}
		// 新建
		return saveGpsApplyback(applyBackParam, ApplyStatus.UNCOMMITTED);
	}

	@Override
	public GpsApplyback submitGpsApplyback(ApplybackParam applyBackParam) {
		Assert.notNull(applyBackParam, "请求数据获取错误");
		logger.info("退回申请提交:" + JSON.toJSONString(applyBackParam));
		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(applyBackParam.getApplybackId())) {
			return updateGpsApplyback(applyBackParam, ApplybackStatus.SUBMITTED);
		}
		// 新建
		return saveGpsApplyback(applyBackParam, ApplyStatus.SUBMITTED);
	}

	private GpsApplyback saveGpsApplyback(ApplybackParam applyBackParam, ApplyStatus applyStatus) {
		// 保存退货申请单
		GpsApplyback applyBack = new GpsApplyback();
		String applybackId = BaseUtils.get16UUID();
		applyBack.setApplybackId(applybackId);
		applyBack.setBranchId(applyBackParam.getBranchId());
		applyBack.setBranchName(applyBackParam.getBranchName());
		applyBack.setAcreateCcountId(applyBackParam.getCreateAccountId());
		applyBack.setApplyTime(Calendar.getInstance().getTime());
		applyBack.setApplybackStatus(applyStatus.getCode());
		applyBack.setApplybackNote(applyBackParam.getApplybackNote());
		applyBack.setExpressCompany(applyBackParam.getExpressCompany());
		applyBack.setExpressNo(applyBackParam.getExpressNo());
		gpsApplybackMapper.insertSelective(applyBack);
		logger.info("退回申请保存:" + JSON.toJSONString(applyBack));
		// 保存退货申请单明细
		List<ApplybackDetailParam> applyBackDetailList = applyBackParam.getApplyBackDetailParamList();
		for (ApplybackDetailParam applybackDetailParam : applyBackDetailList) {
			GpsApplybackDetail applyBackDetail = new GpsApplybackDetail();
			applyBackDetail.setApplybackDetailId(BaseUtils.get16UUID());
			applyBackDetail.setApplybackId(applybackId);
			applyBackDetail.setBrandId(applybackDetailParam.getBrandId());
			applyBackDetail.setGpsId(applybackDetailParam.getGpsId());
			applyBackDetail.setGpsStatus(applybackDetailParam.getGpsStatus());
			applyBackDetail.setSupplierId(applybackDetailParam.getSupplierId());
			applyBackDetail.setSupplierName(applybackDetailParam.getSupplierName());
			applyBackDetail.setGpsCategory(applybackDetailParam.getGpsCategory());
			applyBackDetail.setApplybackDetailStatus(ApplybackDetailStatus.WAITING.getCode());
			gpsApplybackDetailMapper.insertSelective(applyBackDetail);
			logger.info("退回申请明细插入:" + JSON.toJSONString(applyBackDetail));
		}
		return applyBack;
	}

	private GpsApplyback updateGpsApplyback(ApplybackParam applyBackParam, ApplybackStatus applybackStatus) {

		GpsApplyback applyBack = new GpsApplyback();
		applyBack.setApplybackId(applyBackParam.getApplybackId());
		String applybackNote = applyBackParam.getApplybackNote() == null ? "" : applyBackParam.getApplybackNote();
		applyBack.setApplybackNote(applybackNote);
		applyBack.setApplybackStatus(applybackStatus.getCode());
		applyBack.setExpressCompany(applyBackParam.getExpressCompany());
		applyBack.setExpressNo(applyBackParam.getExpressNo());
		gpsApplybackMapper.updateByPrimaryKeySelective(applyBack);
		logger.info("退回申请修改:" + JSON.toJSONString(applyBack));
		// 删除退货申请单明细
		String applybackId = applyBackParam.getApplybackId();
		List<GpsApplybackDetail> detailList = gpsApplybackDetailMapper.selectApplybackDetailByApplybackId(applybackId);
		for (GpsApplybackDetail gpsApplybackDetail : detailList) {
			gpsApplybackDetailMapper.delete(gpsApplybackDetail);
		}
		// 保存退货申请单明细
		List<ApplybackDetailParam> applyBackDetailList = applyBackParam.getApplyBackDetailParamList();
		for (ApplybackDetailParam applybackDetailParam : applyBackDetailList) {
			GpsApplybackDetail applyBackDetail = new GpsApplybackDetail();
			applyBackDetail.setApplybackDetailId(BaseUtils.get16UUID());
			applyBackDetail.setApplybackId(applybackId);
			applyBackDetail.setBrandId(applybackDetailParam.getBrandId());
			applyBackDetail.setGpsId(applybackDetailParam.getGpsId());
			applyBackDetail.setGpsStatus(applybackDetailParam.getGpsStatus());
			applyBackDetail.setSupplierId(applybackDetailParam.getSupplierId());
			applyBackDetail.setSupplierName(applybackDetailParam.getSupplierName());
			applyBackDetail.setGpsCategory(applybackDetailParam.getGpsCategory());
			applyBackDetail.setApplybackDetailStatus(ApplybackDetailStatus.WAITING.getCode());
			gpsApplybackDetailMapper.insertSelective(applyBackDetail);
			logger.info("退回申请明细插入:" + JSON.toJSONString(applyBackDetail));
		}
		return applyBack;
	}

	/**
	 * @param applybackDetailId
	 * @param applyBackDetail
	 * @return
	 */
	public GpsApplyback checkOper(String applybackDetailId, GpsApplybackDetail applyBackDetail) {
		Assert.notNull(applyBackDetail, "未找到对应退货申请明细:" + applybackDetailId);
		GpsApplyback applyBack = gpsApplybackMapper.selectByPrimaryKey(applyBackDetail.getApplybackId());
		Assert.notNull(applyBack, "未找到对应退货申请:" + applyBackDetail.getApplybackId());
		Assert.isTrue(StringUtils.equals(ApplybackStatus.SUBMITTED.getCode(), applyBack.getApplybackStatus()), "只能对已提交待确认收货的退货申请操作");
		Assert.isTrue(StringUtils.equals(ApplybackDetailStatus.WAITING.getCode(), applyBackDetail.getApplybackDetailStatus()), "只能对待确认的退货申请明细操作");
		return applyBack;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void testtest(GpsApplybackDetail applyBackDetail){
		storeService.delete(applyBackDetail.getGpsId());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public GpsApplybackDetail ackApplybackDetail(String applybackDetailId, String operUserId) {
		Assert.isTrue(StringUtils.isNotBlank(applybackDetailId), "退货申请明细编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(operUserId), "操作用户编号不能为空");
		GpsApplybackDetail applyBackDetail = gpsApplybackDetailMapper.selectByPrimaryKey(applybackDetailId);
		GpsApplyback applyBack = checkOper(applybackDetailId, applyBackDetail);
		// 确认收货
		applyBackDetail.setApplybackDetailStatus(ApplybackDetailStatus.RECEIVED.getCode());
		gpsApplybackDetailMapper.updateByPrimaryKeySelective(applyBackDetail);
		// 入潽金库
		GpsStorePj gpsStorePj = new GpsStorePj();
		gpsStorePj.setBranchId(applyBack.getBranchId());
		gpsStorePj.setBrandId(applyBackDetail.getBrandId());
		gpsStorePj.setGpsId(applyBackDetail.getGpsId());
		gpsStorePj.setGpsCategory(applyBackDetail.getGpsCategory());
		gpsStorePj.setGpsStatus(GpsStatus.STORE_FREE.getCode());
		
		GpsStoreBranch storeBranch3 = gpsStoreBranchMapper.selectByGpsId(applyBackDetail.getGpsId());//事物测试
		// 删除经销商库存
		storeService.delete(applyBackDetail.getGpsId());
		storeService.singleAddPjStore(gpsStorePj, operUserId);
		
		// 判断退货申请是否结束
		applybackDone(applyBack);
		return applyBackDetail;
	}

	@Override
	public GpsApplybackDetail updateApplybackDetail(ApplybackDetailParam applybackDetailParam, String operUserId) {
		Assert.notNull(applybackDetailParam, "请求对象不能为空");
		String applybackDetailId = applybackDetailParam.getApplybackDetailId();
		Assert.isTrue(StringUtils.isNotBlank(applybackDetailId), "退货申请明细编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(operUserId), "操作用户编号不能为空");
		GpsApplybackDetail applyBackDetail = gpsApplybackDetailMapper.selectByPrimaryKey(applybackDetailId);
		checkOper(applybackDetailId, applyBackDetail);
		// 确认收货
		BeanUtils.copyProperties(applybackDetailParam, applyBackDetail);
		applyBackDetail.setApplybackDetailStatus(ApplybackDetailStatus.RECEIVED.getCode());
		gpsApplybackDetailMapper.updateByPrimaryKeySelective(applyBackDetail);
		return applyBackDetail;
	}

	@Override
	public GpsApplybackDetail missApplybackDetail(String applybackDetailId, String operUserId) {
		Assert.isTrue(StringUtils.isNotBlank(applybackDetailId), "退货申请明细编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(operUserId), "操作用户编号不能为空");
		GpsApplybackDetail applyBackDetail = gpsApplybackDetailMapper.selectByPrimaryKey(applybackDetailId);
		GpsApplyback applyBack = checkOper(applybackDetailId, applyBackDetail);
		// 丢失
		applyBackDetail.setApplybackDetailStatus(ApplybackDetailStatus.MISS.getCode());
		gpsApplybackDetailMapper.updateByPrimaryKeySelective(applyBackDetail);
		// 删除经销商库存
		storeService.delete(applyBackDetail.getGpsId());
		// 判断退货申请是否结束
		applybackDone(applyBack);
		return applyBackDetail;
	}

	@Override
	public void deleteApplybackDetail(String applybackDetailId, String operUserId, String remark) {
		Assert.isTrue(StringUtils.isNotBlank(applybackDetailId), "退货申请明细编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(operUserId), "操作用户编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(remark), "备注不能为空");
		GpsApplybackDetail applyBackDetail = gpsApplybackDetailMapper.selectByPrimaryKey(applybackDetailId);
		Assert.notNull(applyBackDetail, "未找到对应收货清单:" + applybackDetailId);
		GpsApplybackDetailhis gpsApplybackDetailhis = new GpsApplybackDetailhis();
		GpsApplyback applyBack = checkOper(applybackDetailId, applyBackDetail);
		// 移入历史表
		BeanUtils.copyProperties(applyBackDetail, gpsApplybackDetailhis);
		gpsApplybackDetailhis.setCreateAccountId(operUserId);
		gpsApplybackDetailhis.setOperTime(new Date());
		gpsApplybackDetailhis.setRemark(remark);
		gpsApplybackDetailhisMapper.insert(gpsApplybackDetailhis);
		// 删除
		gpsApplybackDetailMapper.delete(applyBackDetail);
		// 判断退货申请是否结束
		applybackDone(applyBack);
	}

	/**
	 * 结束退货申请
	 * 
	 * @param applyBack
	 */
	public void applybackDone(GpsApplyback applyBack) {
		Assert.notNull(applyBack, "退货申请不能为空");
		Assert.isTrue(StringUtils.isNotBlank(applyBack.getApplybackId()), "退货申请编号不能为空");
		int count = gpsApplybackDetailMapper.countNotAckApplybackDetail(applyBack.getApplybackId(), ApplybackDetailStatus.WAITING.getCode());
		if (count == 0) {
			applyBack.setApplybackStatus(ApplybackStatus.DONE.getCode());
			gpsApplybackMapper.updateByPrimaryKeySelective(applyBack);
		}
	}

	@Override
	public GpsApplyback selectByApplybackId(String applybackId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<ApplyBackGpsVo> getWaitBackGpsList(String branchId, String branchStatus) {

		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAllGpsBrand();
		// 查询所有供应商
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		// 如果经销商在线
		List<GpsStoreBranch> gpsStoreBranchList = gpsStoreBranchMapper.selectWaitBackGpsOnline(branchId);
		List<ApplyBackGpsVo> gpsList = new ArrayList<ApplyBackGpsVo>();
		for (GpsStoreBranch currGps : gpsStoreBranchList) {
			ApplyBackGpsVo tempApplyBackGps = new ApplyBackGpsVo();
			BeanUtils.copyProperties(currGps, tempApplyBackGps);
			// 设置品牌名
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(currGps.getBrandId())) {
					tempApplyBackGps.setBrandName(gpsBrand.getBrandName());
				}
			}
			// 设置供应商名称
			for (GpsSupplier gpsSupplier : gpsSupplierList) {
				if (gpsSupplier.getSupplierId().equals(tempApplyBackGps.getSupplierId())) {
					tempApplyBackGps.setSupplierName(gpsSupplier.getSupplierName());
				}
			}
			if ("WIRE".equals(tempApplyBackGps.getGpsCategory())) {
				tempApplyBackGps.setGpsCategoryName("有线");
			} else {
				tempApplyBackGps.setGpsCategoryName("无线");
			}
			GpsStatus gpsStatus = GpsStatus.fromValue(tempApplyBackGps.getGpsStatus());
			if (gpsStatus != null)
				tempApplyBackGps.setGpsStatusName(gpsStatus.getRemark());
			gpsList.add(tempApplyBackGps);
		}

		// 如果经销商不在线
		return gpsList;
	}

	@Override
	public List<ApplyBackGpsVo> getSelectedBackGpsList(String applybackId) {

		// 查询所有品牌
		List<GpsBrand> gpsBrandList = gpsBrandMapper.selectAllGpsBrand();
		// 查询所有供应商
		List<GpsSupplier> gpsSupplierList = gpsSupplierMapper.selectAll();
		// 查询退货申请单
		GpsApplyback apply = gpsApplybackMapper.selectByPrimaryKey(applybackId);
		// 查询申请单明细
		List<GpsApplybackDetail> applybackDetailList = gpsApplybackDetailMapper.selectApplybackDetailByApplybackId(applybackId);

		List<ApplyBackGpsVo> gpsList = new ArrayList<ApplyBackGpsVo>();
		for (GpsApplybackDetail gpsApplybackDetail : applybackDetailList) {
			ApplyBackGpsVo tempApplyBackGps = new ApplyBackGpsVo();
			tempApplyBackGps.setApplybackId(apply.getApplybackId());
			tempApplyBackGps.setBranchId(apply.getBranchId());
			tempApplyBackGps.setBranchName(apply.getBranchName());
			BeanUtils.copyProperties(gpsApplybackDetail, tempApplyBackGps);
			// 设置品牌名
			for (GpsBrand gpsBrand : gpsBrandList) {
				if (gpsBrand.getBrandId().equals(gpsApplybackDetail.getBrandId())) {
					tempApplyBackGps.setBrandName(gpsBrand.getBrandName());
				}
			}
			// 设置供应商名称
			for (GpsSupplier gpsSupplier : gpsSupplierList) {
				if (gpsSupplier.getSupplierId().equals(tempApplyBackGps.getSupplierId())) {
					tempApplyBackGps.setSupplierName(gpsSupplier.getSupplierName());
				}
			}
			if ("WIRE".equals(tempApplyBackGps.getGpsCategory())) {
				tempApplyBackGps.setGpsCategoryName("有线");
			} else {
				tempApplyBackGps.setGpsCategoryName("无线");
			}
			GpsStatus gpsStatus = GpsStatus.fromValue(tempApplyBackGps.getGpsStatus());
			if (gpsStatus != null)
				tempApplyBackGps.setGpsStatusName(gpsStatus.getRemark());
			ApplybackDetailStatus applybackDetailStatus = ApplybackDetailStatus.fromValue(tempApplyBackGps.getApplybackDetailStatus());
			if(applybackDetailStatus != null){
				tempApplyBackGps.setApplybackDetailStatusName(applybackDetailStatus.getRemark());
			}
			gpsList.add(tempApplyBackGps);
		}

		/*
		 * //如果经销商在线 List<GpsStoreBranch> gpsStoreBranchList = gpsStoreBranchMapper.selectWaitBackGpsOnline(branchId); List<ApplyBackGpsVo> gpsList= new ArrayList<ApplyBackGpsVo>(); for (GpsStoreBranch currGps : gpsStoreBranchList) { ApplyBackGpsVo tempApplyBackGps = new ApplyBackGpsVo(); BeanUtils.copyProperties(currGps, tempApplyBackGps); //设置品牌名 for (GpsBrand gpsBrand : gpsBrandList) { if(gpsBrand.getBrandId().equals(currGps.getBrandId())){ tempApplyBackGps.setBrandName(gpsBrand.getBrandName()); } }
		 * //设置供应商名称 for (GpsSupplier gpsSupplier : gpsSupplierList) { if(gpsSupplier.getSupplierId().equals(tempApplyBackGps.getSupplierId())){ tempApplyBackGps.setSupplierName(gpsSupplier.getSupplierName()); } } if("WIRE".equals(tempApplyBackGps.getGpsCategory())){ tempApplyBackGps.setGpsCategoryName("有线"); }else{ tempApplyBackGps.setGpsCategoryName("无线"); } GpsStatus gpsStatus = GpsStatus.fromValue(tempApplyBackGps.getGpsStatus()); if(gpsStatus != null)
		 * tempApplyBackGps.setGpsStatusName(gpsStatus.getRemark()); gpsList.add(tempApplyBackGps); }
		 */
		// 如果经销商不在线
		return gpsList;
	}

	@Override
	public List<GpsApplybackDetailhis> selectHisByApplybackId(String applybackId) {
		Assert.isTrue(StringUtils.isNotBlank(applybackId), "退货申请编号不能为空");
		return gpsApplybackDetailhisMapper.selectApplybackDetailhisByApplybackId(applybackId);
	}

	@Override
	public GpsApplybackDetail saveGpsApplybackDetail(GpsApplybackDetail gpsApplybackDetail) {
		/**
		 * 判断GPS编码是否存在于经销商库存
		 */
		GpsApplyback gpsApplyback = gpsApplybackMapper.selectByPrimaryKey(gpsApplybackDetail.getApplybackId());
		String branchId = gpsApplyback == null ? "" : gpsApplyback.getBranchId();
		GpsStoreBranchVo gpsStoreBranchVo = new GpsStoreBranchVo();
		gpsStoreBranchVo.setBranchId(branchId);
		gpsStoreBranchVo.setGpsId(gpsApplybackDetail.getGpsId());
		List<GpsStoreBranch> gpsStoreBranchList = gpsStoreBranchMapper.selectStoreBranchByParams(gpsStoreBranchVo);
		Assert.isTrue(gpsStoreBranchList.size() == 1, "GPS编码【"+gpsApplybackDetail.getGpsId()+"】在经销商【"+branchId+"】库存中不存在!");
		
		GpsApplybackDetail oldApplybackDetail = gpsApplybackDetailMapper.selectApplybackDetailByGpsId(gpsApplybackDetail.getGpsId());
		if(gpsApplybackDetail.getApplybackDetailId() == null){
			Assert.isTrue(oldApplybackDetail == null, "退货申请明细已存在GPS编码 "+gpsApplybackDetail.getGpsId());
			gpsApplybackDetail.setApplybackDetailId(BaseUtils.get16UUID());
			gpsApplybackDetailMapper.insertSelective(gpsApplybackDetail);
		}else{
			if(oldApplybackDetail != null){
				Assert.isTrue(gpsApplybackDetail.getApplybackDetailId().equals(oldApplybackDetail.getApplybackDetailId())
						, "退货申请明细已存在GPS编码 "+gpsApplybackDetail.getGpsId());
			}
			gpsApplybackDetailMapper.updateByPrimaryKeySelective(gpsApplybackDetail);
		}
		return gpsApplybackDetail;
	}
}
