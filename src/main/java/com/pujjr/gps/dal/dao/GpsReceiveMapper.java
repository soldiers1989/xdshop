package com.pujjr.gps.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pujjr.gps.dal.base.SuperMapper;
import com.pujjr.gps.dal.domain.ExpressReceipt;
import com.pujjr.gps.dal.domain.GpsReceive;

public interface GpsReceiveMapper extends SuperMapper<GpsReceive> {

	/*int deleteByPrimaryKey(String receiveId);

	int insert(GpsReceive record);

	int insertSelective(GpsReceive record);

	GpsReceive selectByPrimaryKey(String receiveId);

	int updateByPrimaryKeySelective(GpsReceive record);

	int updateByPrimaryKey(GpsReceive record);*/

	@Select("select * from t_gps_receive where detail_id = #{detailId}")
	public List<GpsReceive> selectByDetailId(String detailId);

	/**
	 * 根据申请单号，查询申请单快递单列表
	 * @param applyId
	 * @param gpsStatus
	 * @return
	 */
	public List<ExpressReceipt> selectExpressReceiptByApplyId(@Param("applyId")String applyId,@Param("gpsStatus")String gpsStatus);
	
	/**
	 * 根据快递单号，查询收货表gps信息
	 * @param expressNo
	 * @return
	 */
	public List<GpsReceive> selectGpsRecieveListByExpressNo(String expressNo);
	/**
	 * 更具订单明细编码，查询收货表gps信息
	 * @param detailId 订单明细编码
	 * @param gpsStatus 收货信息表GPS状态
	 * @return
	 */
	public List<GpsReceive> selectGpsReceiveListByDetailId(@Param("detailId")String detailId,@Param("gpsStatus")String gpsStatus);

	/**
	 * 更具gps编号，查询gps收货信息
	 * @param gpsId
	 * @return
	 */
	GpsReceive selectByGpsId(String gpsId);
	
	/**
	 * 根据gps编号和经销商编号，查询gps收货信息
	 * 160068
	 * 2018年8月20日 下午3:58:04
	 * @param gpsId
	 * @param branchId
	 * @return
	 */
	List<GpsReceive> selectByGpsIdAndBranchCode(@Param("gpsId") String gpsId,@Param("branchId") String branchId);
}