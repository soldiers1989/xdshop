package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsInstorePj;
import com.pujjr.gps.dal.domain.GpsStorePj;

public interface GpsStorePjMapper extends SuperMapper<GpsStorePj> {
	/**
	 * 条件查询潽金库存
	 * @param storePjParam
	 * @return
	 */
	public List<GpsStorePj> selectStorePjByParams(@Param("storePjParam") StorePjParam storePjParam);
	
	/**
	 * 根据GPSID查询库存
	 * 
	 * @param gpsId
	 * @return
	 */
	public GpsStorePj selectStorePjByGpsId(String gpsId);
	
	/**
	 * 潽金库存批量导入
	 * @param list
	 */
	public void batchAddStorePj(List<GpsStorePj> list);
	
	/**
	 * 潽金库存批量写入入库记录
	 * @param list
	 */
	public void batchAddInstorePj(List<GpsInstorePj> list);
	
	/**
	 * 根据gps编码删除潽金库存
	 * @author tom
	 * @time 2017年12月27日 下午2:23:25
	 * @param gpsId gps编码
	 * @return 已删除gps
	 */
	public void deleteByGpsId(String gpsId);

}