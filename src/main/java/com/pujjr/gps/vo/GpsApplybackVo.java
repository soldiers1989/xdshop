package com.pujjr.gps.vo;

import java.util.List;

import com.pujjr.gps.dal.domain.GpsApplyback;
import com.pujjr.gps.dal.domain.GpsApplybackDetail;
import com.pujjr.gps.dal.domain.GpsCheckhis;

public class GpsApplybackVo extends GpsApplyback{
	private String applybackStatusName;
	/**
	 * 退货申请明细
	 */
	private List<GpsApplybackDetail> applyBackDetail;
	/**
	 * 退货申请审核审批历史记录
	 */
	private List<GpsCheckhis> applyBackCheckList;
	
	
	public List<GpsCheckhis> getApplyBackCheckList() {
		return applyBackCheckList;
	}
	public void setApplyBackCheckList(List<GpsCheckhis> applyBackCheckList) {
		this.applyBackCheckList = applyBackCheckList;
	}
	public String getApplybackStatusName() {
		return applybackStatusName;
	}
	public void setApplybackStatusName(String applybackStatusName) {
		this.applybackStatusName = applybackStatusName;
	}
	public List<GpsApplybackDetail> getApplyBackDetail() {
		return applyBackDetail;
	}
	public void setApplyBackDetail(List<GpsApplybackDetail> applyBackDetail) {
		this.applyBackDetail = applyBackDetail;
	}
	
}
