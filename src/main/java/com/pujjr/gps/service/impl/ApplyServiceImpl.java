package com.pujjr.gps.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.BaseIterableUtils;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.ApplyParam;
import com.pujjr.gps.api.ApplySearchParam;
import com.pujjr.gps.api.LinkmanParam;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsLinkmanMapper;
import com.pujjr.gps.dal.dao.GpsOrderDetailMapper;
import com.pujjr.gps.dal.dao.GpsStocktakeMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsApplyDo;
import com.pujjr.gps.dal.domain.GpsCheckhis;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.enumeration.ApplyStatus;
import com.pujjr.gps.enumeration.ApplyType;
import com.pujjr.gps.enumeration.OrderDetailStatus;
import com.pujjr.gps.enumeration.StocktakeType;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.CheckhisService;
import com.pujjr.gps.service.StoreService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.vo.GpsApplyVo;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class ApplyServiceImpl extends BaseService<ApplyService> implements ApplyService {

	@Autowired
	GpsApplyMapper gpsApplyMapper;

	@Autowired
	GpsOrderDetailMapper gpsOrderDetailMapper;

	@Autowired
	GpsLinkmanMapper gpsLinkmanMapper;

	@Autowired
	CheckhisService checkhisService;
	@Autowired
	private GpsStocktakeMapper gpsStocktakeMapper;
	@Autowired
	private StoreService storeServiceImpl;

	@Override
	public GpsApply selectBaseApplyByApplyId(String applyId) {
		// 获取申请单
		Assert.isTrue(StringUtils.isNotBlank(applyId), "申请单编号不能为空");
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyId);
		Assert.notNull(gpsApply, "申请单编号对应的申请单记录不存在");
		return gpsApply;
		// TODO 自动生成的方法存根
	}

	/**
	 * @param gpsApply
	 * @return
	 */
	@Override
	public GpsLinkman selectLinkmanByLinkmanId(String linkmanId) {
		// 获取联系人
//		Assert.isTrue(StringUtils.isNotBlank(linkmanId), "联系人编号不能为空");
		GpsLinkman gpsLinkman = gpsLinkmanMapper.selectByPrimaryKey(linkmanId);
//		Assert.notNull(gpsLinkman, "申请单编号对应的联系人记录不存在");
		return gpsLinkman;
	}

	@Override
	public GpsApply selectDetailByApplyId(String applyId) {
		// 获取申请单
		GpsApply gpsApply = selectByApplyId(applyId);
		GpsLinkman gpsLinkman = selectLinkmanByLinkmanId(gpsApply.getLinkmanId());
		// 获取审批审核信息
		List<GpsCheckhis> gpsCheckhisList = checkhisService.selectByPubId(applyId);
		// 存入
		gpsApply.setGpsLinkman(gpsLinkman);
		gpsApply.setGpsCheckhisList(gpsCheckhisList);
		//获取库存盘点信息
		GpsStocktake gpsStocktake = gpsStocktakeMapper.selectGpsStocktakeById(gpsApply.getStocktakeId());
		gpsApply.setGpsStocktake(gpsStocktake);
		return gpsApply;
	}

	@Override
	public GpsApply selectByApplyId(String applyId) {
		// 获取申请单
		Assert.isTrue(StringUtils.isNotBlank(applyId), "申请单编号不能为空");
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyId);
		Assert.notNull(gpsApply, "申请单编号对应的申请单记录不存在");
		// 已使用GPS
		addUsedNum(gpsApply);
		return gpsApply;
		// TODO 自动生成的方法存根
	}

	@Override
	public PageInfo<GpsApply> gpsApplyList(ApplySearchParam applySearchParam, String applyStatus) {
		Assert.notNull(applySearchParam, "申请单查询请求错误");
		// 分页
		Page<GpsApply> page = PageHelper.startPage(applySearchParam.getPageNum(), applySearchParam.getPageSize());
		
		/*
		// 查询条件
		Example example = new Example(GpsApply.class);
		Example.Criteria criteria = example.createCriteria();
		if (applySearchParam.getBranchId() != null)
			criteria.andEqualTo("branchId", applySearchParam.getBranchId());
		if (applySearchParam.getApplyId() != null)
			criteria.andEqualTo("applyId", applySearchParam.getApplyId());
		try {
			// 查询日期2017-11-27 00:00:00.0
			Date dateNow = applySearchParam.getApplyTime();
			// 查询日期后一天：2017-11-28 00:00:00.0
			Date dateTomorrow = Utils.getDateAfterDay(dateNow, 1);
			criteria.andBetween("applyTime", dateNow, dateTomorrow);
		} catch (Exception e) {
		}
		if (StringUtils.isNotBlank(applyStatus)) {
			criteria.andIn("applyStatus", Arrays.asList(StringUtils.split(applyStatus, "|")));
		}
		// 排序
		example.orderBy("applyTime");
		gpsApplyMapper.selectByExample(example);*/
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("applySearchParam", applySearchParam);
		if (StringUtils.isNotBlank(applyStatus)) {
			paramMap.put("statusList", Arrays.asList(StringUtils.split(applyStatus, "|")));
		}
		gpsApplyMapper.selectAllApplyByParamMap(paramMap);
		PageInfo<GpsApply> pageInfo = new PageInfo<GpsApply>(page);
		List<GpsApply> list = pageInfo.getList();
		for (GpsApply gpsApply : list) {
			addUsedNum(gpsApply);
		}

		return pageInfo;
	}

	/**
	 * 往申请单添加已使用GPS数
	 * 
	 * @param gpsApply
	 * @return
	 */
	private GpsApply addUsedNum(GpsApply gpsApply) {
		Assert.notNull(gpsApply, "申请单信息获取错误");
		Assert.notNull(StringUtils.isNotBlank(gpsApply.getApplyId()), "获取申请单已使用GPS数出错,申请单编号不能为空");
		// 获取订单明细
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectByApplyId(gpsApply.getApplyId());
		gpsApply.setGpsOrderDetailList(gpsOrderDetailList);
		// 统计
		int unusedWireNum = gpsApply.getWireNum();
		int unusedWirelessNum = gpsApply.getWirelessNum();
		try {
			for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
				unusedWireNum = unusedWireNum - gpsOrderDetail.getWireNum();
				unusedWirelessNum = unusedWirelessNum - gpsOrderDetail.getWirelessNum();
			}
		} catch (Exception e) {
			logger.error("获取已使用GPS数出错");
			e.printStackTrace();
		}
		gpsApply.setUnusedWireNum(unusedWireNum);
		gpsApply.setUnusedWirelessNum(unusedWirelessNum);
		return gpsApply;
	}

	@Override
	public GpsApply tempSaveGpsApply(ApplyParam applyParam,StocktakeType stocktakeType) {
		Assert.notNull(applyParam, "请求数据获取错误");
		logger.info("申请单暂存:" + JSON.toJSONString(applyParam));
		GpsApply gpsApply = new GpsApply();
		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(applyParam.getApplyId())) {
			gpsApply = updateGpsApply(applyParam, ApplyStatus.UNCOMMITTED);
		} else {
			// 新建
			gpsApply = saveGpsApply(applyParam, ApplyStatus.UNCOMMITTED,stocktakeType);
		}
		return gpsApply;
	}

	@Override
	public GpsApply submitGpsApply(ApplyParam applyParam,StocktakeType stocktakeType) {
		String stocktakeId = BaseUtils.get16UUID();
		/**
		 * 新增盘点信息
		 */
		GpsStocktake gpsStocktake = applyParam.getGpsStocktake();
		Assert.notNull(applyParam, "请求数据获取错误");
		logger.info("申请单手动提交:" + JSON.toJSONString(applyParam));
		GpsApply gpsApply = null;
		/**
		 * 申请单号不为空，表名当前经销商存在未完成申请单 执行：保存+提交
		 */
		Assert.isTrue(checkUnfinishedApply(applyParam.getBranchId()), "提交失败：当前存在未完成申请单，无法提交新申请");

		// 申请单编号不为空则更新
		if (StringUtils.isNotBlank(applyParam.getApplyId())) {
			gpsApply = updateGpsApply(applyParam, ApplyStatus.SUBMITTED);
		} else {
			gpsApply = saveGpsApply(applyParam, ApplyStatus.SUBMITTED,stocktakeType);
		}
		checkhisService.submitApply(gpsApply);
		gpsApply.setGpsStocktake(gpsStocktake);
		return gpsApply;
	}

	@Override
	public GpsApply meteoricRise(ApplyParam applyParam,StocktakeType stocktakeType) {
		Assert.notNull(applyParam, "请求数据获取错误");
		logger.info("申请单自动提交:" + JSON.toJSONString(applyParam));
		GpsApply gpsApply = null;
		if (checkUnfinishedApply(applyParam.getBranchId())) {
			// 申请单编号不为空则更新
			if (StringUtils.isNotBlank(applyParam.getApplyId())) {
//				gpsApply = updateGpsApply(applyParam, ApplyStatus.APPROVE_PASS);
				gpsApply = updateGpsApply(applyParam, ApplyStatus.SUBMITTED);
				// 记录
			} else {
//				gpsApply = saveGpsApply(applyParam, ApplyStatus.APPROVE_PASS,stocktakeType);
				gpsApply = saveGpsApply(applyParam, ApplyStatus.SUBMITTED,stocktakeType);
				// 记录
			}
		}
		// 提交
		return gpsApply;
	}

	/**
	 * 检查是否有未完成的申请单
	 * 
	 * @return
	 */
	public boolean checkUnfinishedApply(String branchId) {
		List<GpsApply> unFinishApply = gpsApplyMapper.selectAllUnFinishApply(branchId);
		return BaseIterableUtils.isEmpty(unFinishApply);
	}

	@Override
	public void updateWaitReceiveOrderNum(String applyId, int number) {
		GpsApply gpsApply = selectBaseApplyByApplyId(applyId);
		int waitReceiveOrderNum = gpsApply.getWaitReceiveOrderNum() + number;
		gpsApply.setWaitReceiveOrderNum(waitReceiveOrderNum);
		gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
	}

	/**
	 * 保存申请单
	 * 
	 * @param applyParam
	 * @param applyStatus
	 * @param stocktakeType 区分是单笔保存还是批量保存申请单。批量保存：每月1号盘点，批量生成申请单
	 * @return
	 */
	public GpsApply saveGpsApply(ApplyParam applyParam, ApplyStatus applyStatus,StocktakeType stocktakeType) {
		/**
		 * 校验是否存在未提交申请单
		 * 存在未提交申请单，禁止保存新申请单
		 */
		GpsApply unCommitGpsApply = gpsApplyMapper.selectUnCommitApply(applyParam.getBranchId());
		String unCommitApplyId = unCommitGpsApply == null ? "" : unCommitGpsApply.getApplyId();
	
		Assert.notNull(applyParam, "请求数据获取错误");
		GpsApply gpsApply = new GpsApply();
		/**
		 * 存在为提交申请单，不再新增申请单
		 */
		if(unCommitGpsApply == null){
			/**
			 * 保存盘点数据
			 * 单笔提交的申请：插入盘点表记录，盘点类型为：single
			 */
			String stocktakeId = "";
			if(stocktakeType.equals(StocktakeType.SINGLE)){
				stocktakeId = BaseUtils.get16UUID();
				GpsStocktake gpsStocktake = storeServiceImpl.tempCheckBranchStore(applyParam.getBranchId());
				gpsStocktake.setStocktakeId(stocktakeId);
				gpsStocktake.setCreateTime(Calendar.getInstance().getTime());
				gpsStocktake.setStocktakeType(StocktakeType.SINGLE.getCode());
				gpsStocktake.setBranchId(applyParam.getBranchId());
				gpsStocktake.setBranchName(applyParam.getBranchName());
				gpsStocktakeMapper.insertSelective(gpsStocktake);
			}else{
				stocktakeId = applyParam.getStocktakeId();
			}
			// 申请单参数复制
			BeanUtils.copyProperties(applyParam, gpsApply);
			// 申请单参数合法性检查
			applyCheck(gpsApply);
			// 保存申请单
			gpsApply.setApplyId(BaseUtils.get16UUID());
			gpsApply.setApplyTime(new Date());
			gpsApply.setApplyStatus(applyStatus.getCode());
			gpsApply.setFreeWireNum(gpsApply.getWireNum());
			gpsApply.setFreeWirelessNum(gpsApply.getWirelessNum());
			gpsApply.setUsedWireNum(0);
			gpsApply.setUsedWirelessNum(0);
			gpsApply.setTotalWireNum(gpsApply.getWireNum());
			gpsApply.setTotalWirelessNum(gpsApply.getWirelessNum());
			gpsApply.setStocktakeId(stocktakeId);
			
			gpsApplyMapper.insert(gpsApply);
			logger.info("保存申请单:" + JSON.toJSONString(gpsApply));
		}else{
			/**
			 * 单笔新增时，存在未提交申请单，向前端抛出异常
			 */
			if(stocktakeType.equals(StocktakeType.SINGLE)){
				Assert.isTrue(unCommitGpsApply == null, "已存在未提交申请单,申请单编号【"+unCommitApplyId+"】");
			}
		}
		return gpsApply;
	}

	/**
	 * 更新申请单
	 * 
	 * @param applyParam
	 * @param applyStatus
	 * @return
	 */
	public GpsApply updateGpsApply(ApplyParam applyParam, ApplyStatus applyStatus) {
		Assert.notNull(applyParam, "请求数据获取错误");
		Assert.isTrue(StringUtils.isNotBlank(applyParam.getApplyId()), "申请单编号不能为空");
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyParam.getApplyId());
		Assert.notNull(gpsApply, "申请单编号对应的申请单记录不存在");
		// 申请单参数修改
		gpsApply.setWireNum(applyParam.getWireNum());
		gpsApply.setWirelessNum(applyParam.getWirelessNum());
		gpsApply.setFreeWireNum(gpsApply.getWireNum());
		gpsApply.setFreeWirelessNum(gpsApply.getWirelessNum());
		gpsApply.setUsedWireNum(0);
		gpsApply.setUsedWirelessNum(0);
		gpsApply.setTotalWireNum(gpsApply.getWireNum());
		gpsApply.setTotalWirelessNum(gpsApply.getWirelessNum());
		gpsApply.setRemark(applyParam.getRemark());
		//20180101 add
		gpsApply.setLinkmanId(applyParam.getLinkmanId());
		/*//手动创建申请单
		if("MANUAL".equals(applyParam.getApplyType())){
			gpsApply.setStocktakeId(applyParam.getStocktakeId());
		}*/
		// 申请单参数合法性检查
		applyCheck(gpsApply);

		// 保存申请单;
		gpsApply.setApplyStatus(applyStatus.getCode());
		gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
		logger.info("更新申请单:" + JSON.toJSONString(gpsApply));

		return gpsApply;
	}

	/**
	 * 保存联系人
	 * 
	 * @param linkmanParam
	 * @param branchId
	 * @return
	 */
	public GpsLinkman saveLinkman(LinkmanParam linkmanParam, String branchId) {
		// 联系人参数复制
		GpsLinkman gpsLinkman = new GpsLinkman();
		BeanUtils.copyProperties(linkmanParam, gpsLinkman);
		// 联系人参数合法性检查
		linkmanCheck(gpsLinkman);
		// 保存联系人
		String linkmanId = BaseUtils.get16UUID();
		gpsLinkman.setLinkmanId(linkmanId);
		gpsLinkman.setBranchId(branchId);
		gpsLinkmanMapper.insert(gpsLinkman);
		logger.info("保存联系人:" + JSON.toJSONString(linkmanParam));
		return gpsLinkman;
	}

	/**
	 * 更新联系人
	 * 
	 * @param linkmanParam
	 * @param branchId
	 * @return
	 */
	public GpsLinkman updateLinkman(LinkmanParam linkmanParam, String branchId) {
		// 联系人参数复制
		Assert.isTrue(StringUtils.isNotBlank(linkmanParam.getLinkmanId()), "联系人编号不能为空");
		GpsLinkman gpsLinkman = gpsLinkmanMapper.selectByPrimaryKey(linkmanParam.getLinkmanId());
		Assert.notNull(gpsLinkman, "联系人编号对应的联系人记录不存在");
		// 联系人参数修改
		gpsLinkman.setName(linkmanParam.getName());
		gpsLinkman.setMobile1(linkmanParam.getMobile1());
		gpsLinkman.setAddrProvince(linkmanParam.getAddrProvince());
		gpsLinkman.setAddrCity(linkmanParam.getAddrCity());
		gpsLinkman.setAddrDistrict(linkmanParam.getAddrDistrict());
		gpsLinkman.setAddrDetail(linkmanParam.getAddrDetail());
		// 联系人参数合法性检查
		linkmanCheck(gpsLinkman);
		// 保存联系人
		gpsLinkmanMapper.updateByPrimaryKeySelective(gpsLinkman);
		logger.info("更新联系人:" + JSON.toJSONString(linkmanParam));
		return gpsLinkman;
	}

	/**
	 * @param gpsApply
	 */
	private void applyCheck(GpsApply gpsApply) {
		try{
			Assert.isTrue(StringUtils.isNotBlank(gpsApply.getBranchId()), "经销商编号不能为空");
			Assert.isTrue(StringUtils.isNotBlank(gpsApply.getBranchName()), "经销商名称不能为空");
			Assert.isTrue(StringUtils.isNotBlank(gpsApply.getCreateAccountId()), "创建人不能为空");
			Assert.isTrue(ApplyType.contains(gpsApply.getApplyType()), "申请单类型错误");
			// Assert.isTrue(ApplyChannel.contains(gpsApply.getApplyChannel()), "渠道类型错误");
			Assert.isTrue(StringUtils.length(gpsApply.getRemark()) < GpsApply.REMARK_LENGTH, "备注长度过长");
			Assert.isTrue((gpsApply.getWireNum() != null && gpsApply.getWireNum() >= 0), "有线GPS数错误");
			Assert.isTrue((gpsApply.getWirelessNum() != null && gpsApply.getWirelessNum() >= 0), "无线GPS数错误");
			Assert.isTrue((gpsApply.getWireNum() + gpsApply.getWirelessNum()) > 0, "有线与无线GPS数量相加不能为0");
		}catch(Exception e){
			logger.info("申请单数据核查：");
			logger.error(JSONObject.toJSONString(gpsApply));
		}
		
	}

	/**
	 * @param gpsLinkman
	 */
	private void linkmanCheck(GpsLinkman gpsLinkman) {
		// 联系人合法性检查
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getName()), "联系人姓名不能为空");
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getMobile1()), "联系人联系方式不能为空");
		// 省
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getAddrProvince()), "联系人省份不能为空");
		Assert.isTrue(StringUtils.length(gpsLinkman.getAddrProvince()) < GpsLinkman.ADDR_PROVINCE_LENGTH, "联系人省份过长");
		// 市
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getAddrCity()), "联系人市名称不能为空");
		Assert.isTrue(StringUtils.length(gpsLinkman.getAddrCity()) < GpsLinkman.ADDR_CITY_LENGTH, "联系人市名称过长");
		// 区
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getAddrDistrict()), "联系人区县不能为空");
		Assert.isTrue(StringUtils.length(gpsLinkman.getAddrDistrict()) < GpsLinkman.ADDR_DISTRICT_LENGTH, "联系人区县过长");
		// 详细
		Assert.isTrue(StringUtils.isNotBlank(gpsLinkman.getAddrDetail()), "联系人详细地址不能为空");
		Assert.isTrue(StringUtils.length(gpsLinkman.getAddrDetail()) < GpsLinkman.ADDR_DETAIL_LENGTH, "联系人详细地址过长");
	}

	@Override
	public PageInfo<GpsApplyVo> getWaitApplyList(ApplySearchParam applySearchParam, String applyStatus) {
//		PageHelper.startPage(applySearchParam.getPageNum(), applySearchParam.getPageSize());
		
		List<GpsApplyDo> gpsApplyVoList = gpsApplyMapper.selectWaitApplyList(applySearchParam, applyStatus);
		// 查询申请单经销商联系人
		for (GpsApplyDo gpsApplyDo : gpsApplyVoList) {
			GpsLinkman gpsLinkman = gpsLinkmanMapper.selectDefaultLinkmanByBranchId(gpsApplyDo.getBranchId());
			gpsApplyDo.setGpsLinkman(gpsLinkman);
		}
		PageInfo<GpsApplyDo> pageDo = new PageInfo<GpsApplyDo>(gpsApplyVoList);
		PageInfo<GpsApplyVo> pageVo = new PageInfo<GpsApplyVo>();
		BeanUtils.copyProperties(pageDo, pageVo);
		// PageInfo<GpsApplyVo> pageVo = new PageInfo<GpsApplyVo>();

		/*
		 * BeanUtils.copyProperties(page, pageVo); pageVo.setList(new ArrayList<GpsApplyVo>()); System.err.println(pageVo); List<GpsApplyVo> gpsApplyVoList = pageVo.getList(); for (GpsApplyVo gpsApplyVo : gpsApplyVoList) { String applyId = gpsApplyVo.getApplyId();
		 */
		/**
		 * 查询订单明细,计算当前申请单可用有线、无线数量
		 */
		
		/* List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectGpsOrderDetailByAppyId(applyId); 
		 for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) { 
			 gpsApplyVo.setUnusedWireNum(gpsApplyVo.getUnusedWireNum() - gpsOrderDetail.getWireNum()); 
			 gpsApplyVo.setUnusedWirelessNum(gpsApplyVo.getUnusedWirelessNum() - gpsOrderDetail.getWirelessNum()); 
			 } 
		 }*/
		 
		return pageVo;
	}

	@Override
	public int autoApplySubmit() {
		List<GpsApply> gpsApplyList = gpsApplyMapper.selectAutoApply(ApplyType.AUTO.name(), ApplyStatus.UNCOMMITTED.getCode());
		int count = 0;
		logger.info("开始申请单自动提交");
		if (BaseIterableUtils.isNotEmpty(gpsApplyList)) {
			for (GpsApply gpsApply : gpsApplyList) {
				ApplyParam applyParam = new ApplyParam();
				BeanUtils.copyProperties(gpsApply, applyParam);
				meteoricRise(applyParam,StocktakeType.BATCH);
			}
			count = gpsApplyList.size();
		}
		logger.info("申请单提交完成,提交数量:" + count);
		return count;
	}

	@Override
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public GpsApply changeGpsApplyStatus(String applyId) {
		/**
		 * 更新申请单状态：
		 * 申请单存在空闲设备：待发货
		 * 存在代发货订单明细：待发货
		 * 存在待收货订单明细：待收货
		 * 不存在待收货订单明细，存在未分配gps：待发货。
		 * 不存在待收货订单明细，不存在未分配gps：已收货。
		 * 
		 */
//		String applyId = gpsOrderDetail.getApplyId();
		GpsApply gpsApply = gpsApplyMapper.selectByPrimaryKey(applyId);
		boolean isFreeExist = false;//是否存在空闲的为生成订单的设备
		if(gpsApply.getFreeWirelessNum() > 0 || gpsApply.getFreeWireNum() > 0){
			isFreeExist = true;
		}
		//*******************待发货与待收货互斥，不能同时出现在同一申请的订单明细内********************
		boolean isWaitSend = false;//是否存在待发货订单明细
		boolean isWaitReceive = false;//是否存在待收货明细
		List<GpsOrderDetail> gpsOrderDetailList = gpsOrderDetailMapper.selectByApplyId(applyId);
		for (GpsOrderDetail tempGpsOrderDetail : gpsOrderDetailList) {
			if(tempGpsOrderDetail.getOrderDetailStatus().equals(OrderDetailStatus.WAIT_SEND.getCode())){
				isWaitSend = true;
				break;
			}
		}
		for (GpsOrderDetail tempGpsOrderDetail : gpsOrderDetailList) {
			if(tempGpsOrderDetail.getOrderDetailStatus().equals(OrderDetailStatus.WAIT_RECEIVE.getCode())){
				isWaitReceive = true;
				break;
			}
		}
		if(isFreeExist){
			gpsApply.setApplyStatus(ApplyStatus.APPROVE_PASS.getCode());
		}else if(isWaitSend){
			gpsApply.setApplyStatus(ApplyStatus.WAIT_SEND.getCode());
		}else if(isWaitReceive){
			gpsApply.setApplyStatus(ApplyStatus.WAIT_RECEIVE.getCode());
		}else{
			gpsApply.setApplyStatus(ApplyStatus.HAVE_RECEIVE.getCode());
		}
		
		gpsApplyMapper.updateByPrimaryKeySelective(gpsApply);
		return gpsApply;
	}

}
