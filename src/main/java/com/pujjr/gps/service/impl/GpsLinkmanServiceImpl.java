package com.pujjr.gps.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pujjr.common.utils.BaseUtils;
import com.pujjr.gps.api.GpsLinkmanParam;
import com.pujjr.gps.dal.dao.GpsApplyMapper;
import com.pujjr.gps.dal.dao.GpsLinkmanMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.service.GpsLinkmanService;
import com.pujjr.gps.service.base.BaseService;

@Service
public class GpsLinkmanServiceImpl extends BaseService<GpsLinkmanService> implements GpsLinkmanService {

	@Autowired
	private GpsLinkmanMapper gpsLinkmanMapper;
	@Autowired
	private GpsApplyMapper gpsApplyMapper;
	
	

	@Override
	public PageInfo<GpsLinkman> getGpsLinkmanListByParam(GpsLinkmanParam gpsLinkmanParam) {
		PageHelper.startPage(gpsLinkmanParam.getPageNum(), gpsLinkmanParam.getPageSize());
		List<GpsLinkman> list = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam);
		PageInfo<GpsLinkman> pageDo = new PageInfo<GpsLinkman>(list);
		return pageDo;
	}

	@Override
	public GpsLinkman addGpsLinkman(GpsLinkman gpsLinkman) {
		gpsLinkman.setLinkmanId(BaseUtils.get16UUID());
		gpsLinkmanMapper.insertSelective(gpsLinkman);
		return gpsLinkman;
	}

	@Override
	public GpsLinkman updateGpsLinkman(GpsLinkman gpsLinkman) {
		gpsLinkmanMapper.updateByPrimaryKeySelective(gpsLinkman);
		return gpsLinkman;
	}

	@Override
	public GpsLinkman deleteGpsLinkman(GpsLinkman gpsLinkman) {
		String linkmanId = gpsLinkman.getLinkmanId();
		List<GpsApply> gpsApplyList = gpsApplyMapper.selectApplyByLinkmanId(linkmanId);
		Assert.isTrue(gpsApplyList.size() == 0, "当前联系人："+gpsLinkman.getLinkmanId()+"|"+gpsLinkman.getName()+" 正在使用中,无法执行删除！您可以修改联系人姓名及其他相关信息！");
		gpsLinkmanMapper.deleteByPrimaryKey(linkmanId);
		return gpsLinkman;
	}

	/*
	 * @Override public PageInfo<GpsLinkman> getDefaultGpsLinkman(GpsLinkmanParam gpsLinkmanParam) { PageHelper.startPage(gpsLinkmanParam.getPageNum(), gpsLinkmanParam.getPageSize()); List<GpsLinkman> list = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam); PageInfo<GpsLinkman> pageDo = new PageInfo<GpsLinkman>(list); return pageDo; }
	 */

	@Override
	public GpsLinkman saveGpsLinkman(GpsLinkman gpsLinkman) {
		if (gpsLinkman.getLinkmanId() == null || "".equals(gpsLinkman.getLinkmanId())) {// add
			gpsLinkman.setLinkmanId(BaseUtils.get16UUID());
			gpsLinkman.setIsDefault(false);
			gpsLinkmanMapper.insertSelective(gpsLinkman);
		} else {// update
			gpsLinkmanMapper.updateByPrimaryKeySelective(gpsLinkman);
		}
		logger.info("联系人记录:" + gpsLinkman);
		return gpsLinkman;
	}

	@Override
	public GpsLinkman setDefault(GpsLinkmanParam gpsLinkmanParam) {
		List<GpsLinkman> allLinkMan = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam);
		GpsLinkman gpsLinkman = new GpsLinkman();
		BeanUtils.copyProperties(gpsLinkmanParam, gpsLinkman);
		if (gpsLinkman.getIsDefault()) {
			gpsLinkman.setIsDefault(false);
		} else {
			for (GpsLinkman linkMan : allLinkMan) {
				linkMan.setIsDefault(false);
				gpsLinkmanMapper.updateByPrimaryKeySelective(linkMan);
			}
			gpsLinkman.setIsDefault(true);
		}
		gpsLinkmanMapper.updateByPrimaryKeySelective(gpsLinkman);
		logger.info("设置默认联系人:" + gpsLinkman);
		return gpsLinkman;
	}

	@Override
	public List<GpsLinkman> getGpsLinkmanListByBranchId(String branchId) {
		return gpsLinkmanMapper.selectGpsLinkmanByBranchId(branchId);
	}

	@Override
	public GpsLinkman getDefaultGpsLinkman(String branchId) {
		return gpsLinkmanMapper.selectDefaultLinkmanByBranchId(branchId);
	}

}
