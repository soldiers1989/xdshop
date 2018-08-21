package com.pujjr.gps.service;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import com.pujjr.common.exception.CheckFailException;
import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.enumeration.ExcelTemplate;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.SendFeedbackVO;

/**
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
public interface ExcelService {
	/**
	 * 文件下载
	 * @param response
	 * @param fileName 文件名
	 * @param excelPath 相对路径
	 * @throws Exception
	 */
	public void downloadExcel(HttpServletResponse response,String fileName,String excelPath) throws Exception;

	/**
	 * 导入发货信息反馈
	 * 
	 * @param inputStream
	 * @return
	 * @throws CheckFailException
	 * @throws IOException
	 * @throws Exception
	 */
	SendFeedbackVO importSendFeedback(InputStream inputStream) throws Exception;

	/**
	 * 导出发货订单详情
	 * 
	 * @param orderId
	 * @return
	 * @throws IOException
	 */
	byte[] exportOrderDetail(String orderId) throws Exception;

	/**
	 * 获取完整模板文件名称
	 * 
	 * @param templateName
	 * @return
	 */
	String getFileName(ExcelTemplate template);

	/**
	 * 获取模板文件流
	 * 
	 * @param template
	 * @return
	 * @throws IOException
	 */
	byte[] getExcelTemplate(ExcelTemplate template) throws IOException;
	
	/**
	 * 生成库存盘点信息excel
	 * @param stocktakeParam
	 * @return
	 * @throws Exception
	 */
	String generalStocktakeInfo(StocktakeParam stocktakeParam) throws Exception;
	/**
	 * 生成联系人信息excel
	 * @param branchId
	 * @return
	 * @throws Exception
	 */
	String generalLinkmanInfo(String branchId) throws Exception;
	
	String generalGpsStorePj(StorePjParam storePjParam) throws Exception;
	
	String generalGpsStoreBranch(GpsStoreBranchVo gpsStoreBranchVo) throws Exception;
	
}
