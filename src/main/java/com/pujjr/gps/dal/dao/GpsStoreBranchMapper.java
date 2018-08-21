package com.pujjr.gps.dal.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.GpsInstoreHis;
import com.pujjr.gps.dal.domain.GpsStoreBranch;
import com.pujjr.gps.vo.GpsStoreBranchVo;

public interface GpsStoreBranchMapper extends SuperMapper<GpsStoreBranch> {

	public int updateByPrimaryKey2(GpsStoreBranch gpsStoreBranch);

	/**
	 * 查询可选gps：经销商在线
	 * 
	 * @param branchId
	 *            经销商编号
	 * @return
	 */
	public List<GpsStoreBranch> selectWaitBackGpsOnline(String branchId);

	/**
	 * 查询可选gps：经销商下线
	 * 
	 * @param branchId
	 *            经销商编号
	 * @return
	 */
	public List<GpsStoreBranch> selectWaitBackGpsOffline(String branchId);

	/**
	 * 条件查询经销商库存
	 * 
	 * @param storePjParam
	 * @return
	 */
	public List<GpsStoreBranch> selectStoreBranchByParams(@Param("gpsStoreBranchVo") GpsStoreBranchVo gpsStoreBranchVo);

	/**
	 * 根据GPSID查询库存
	 * 
	 * @param gpsId
	 * @return
	 */
	public GpsStoreBranch selectStoreBranchByGpsId(String gpsId);

	/**
	 * 根据gps编号查库存
	 * 
	 * @param orderId
	 * @return
	 */
	@Select("select * from t_gps_store_branch where gps_id = #{gpsId}")
	public GpsStoreBranch selectByGpsId(String gpsId);

	@Delete("delete from t_gps_store_branch where gps_id = #{gpsId}")
	public int deleteByGpsId(String gps);

	/**
	 * 统计经销商库存
	 * 
	 * @param branchId
	 * @param gpsStatus
	 * @param gpsCategory
	 * @param operTime
	 * @return
	 */
	public Integer countBranchStoreGps(@Param("branchId") String branchId, @Param("gpsStatus") String gpsStatus, @Param("gpsCategory") String gpsCategory, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
	/**
	 * 批量插入经销商库存
	 * @param list
	 */
	public void batchAddBranchStore(List<GpsStoreBranch> list);
	/**
	 * 批量插入经销商入库历史
	 * @param list
	 */
	public void batchAddGpsInstallHis(List<GpsInstoreHis> list);
	
	/**
	 * 获取昨天要上传给赛格的数据
	 * @return
	 */
	public List<GpsStoreBranch> getYesterdayUpploadToSeg();
	
	public List<GpsStoreBranch> getAlluploadDataToSeg();
}