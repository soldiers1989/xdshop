package com.pujjr.gps.service;

import java.io.InputStream;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.pujjr.gps.dal.domain.GpsBrand;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsSend;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface SendService {

	/**
	 * 根据上传的excel文件记录发货信息
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	List<GpsSend> saveGpsSendListByExcelFile(InputStream inputStream, String consigner) throws Exception;

	/**
	 * 品牌列表
	 * 
	 * @return
	 */
	List<GpsBrand> selectAllBrand();

	/**
	 * 根据明细编号查询出货GPS列表
	 * 
	 * @param detailId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<GpsSend> gpsSendListByDetailId(String detailId, int pageNum, int pageSize);

	/**
	 * 提交出货信息
	 * 
	 * @param orderId
	 * @return
	 */
	GpsOrder submitSend(String orderId);

	/**
	 * 根据订单号查询订单下全部的发货清单
	 * 
	 * @param orderId
	 * @return
	 */
	GpsOrder selectAllSendByOrderId(String orderId);

}
