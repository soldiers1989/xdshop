/**
 * 
 */
package com.pujjr.gps.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.controller.base.BaseController;
import com.pujjr.gps.enumeration.ExcelTemplate;
import com.pujjr.gps.service.ExcelService;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.SendFeedbackVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author wen
 * @date 创建时间：2017年6月28日 下午2:30:30
 *
 */
@RestController
@RequestMapping("/excel")
public class ExcelController extends BaseController {

	private static final String WEBKIT = "webkit";
	private static final String FILE_NAME = "fileName";
	@Autowired
	ExcelService excelService;
	@Value("${file.excelPath}")
	private String excelPath;

	@GetMapping("/list")
	@ApiOperation(value = "模板列表")
	public Map<String, String> list() {
		return ExcelTemplate.getMap();
	}

	@GetMapping("template/sendFeedback")
	@ApiOperation(value = "下载发货反馈模板文件")
	public void getSendFeedbackTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = excelService.getFileName(ExcelTemplate.sendFeedback);
		byte[] bytes = excelService.getExcelTemplate(ExcelTemplate.sendFeedback);
		downloadFile(request, response, bytes, fileName, null);
	}

	@PostMapping("/loadExcel")
	@ApiOperation(value = "excel读取测试")
	public SendFeedbackVO loadExcel(MultipartFile file) throws Exception {
		Assert.notNull(file, "上传文件为空");
		SendFeedbackVO list = excelService.importSendFeedback(file.getInputStream());
		return list;
	}

	@GetMapping("/expOrderDetail/{orderId}")
	@ApiOperation(value = "导出订单明细（业务部门：申请单明细）")
	public void expOrderDetail(HttpServletRequest request, HttpServletResponse response, @ApiParam @PathVariable("orderId") String orderId) throws Exception {
		String fileName = excelService.getFileName(ExcelTemplate.orderDetail);
		byte[] bytes = excelService.exportOrderDetail(orderId);
		downloadFile(request, response, bytes, fileName, null);
	}
	
	

	/**
	 * 
	 * @param request
	 * @param response
	 * @param inputStream
	 * @param fileName
	 * @param length
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	private void downloadFile(HttpServletRequest request, HttpServletResponse response, byte[] bytes, String fileName, Integer length) throws UnsupportedEncodingException, IOException {
		downloadFile(request, response, fileName, length);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream());
		bufferedOutputStream.write(bytes);
		response.flushBuffer();
		IOUtils.closeQuietly(bufferedOutputStream);
	}

	/**
	 * @param request
	 * @param response
	 * @param fileName
	 * @param length
	 * @throws UnsupportedEncodingException
	 */
	private void downloadFile(HttpServletRequest request, HttpServletResponse response, String fileName, Integer length) throws UnsupportedEncodingException {
		logger.info("---------------开始下载文件(" + fileName + ")------------------");
		logger.info("文件大小:" + length);
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT).toLowerCase();
		if (!userAgent.contains(WEBKIT)) {// 不是webkit内核则先转码
			fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.displayName());
		}
		fileName = new String((fileName).getBytes(StandardCharsets.UTF_8.displayName()), StandardCharsets.ISO_8859_1.displayName());
		response.addHeader(FILE_NAME, fileName);
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
		if (length != null) {
			response.setContentLength(length);
		}
	}
	
	@GetMapping("/expStocktake")
	@ApiOperation(value = "导出库存盘点信息")
	public void expStocktakeInfo(HttpServletRequest request, HttpServletResponse response, @ApiParam  StocktakeParam stocktakeParam) throws Exception {
		String fileName = excelService.generalStocktakeInfo(stocktakeParam);
		excelService.downloadExcel(response, fileName, excelPath);
	}
	
	@GetMapping("/expLinkman")
	@ApiOperation(value = "导出联系人信息")
	public void expLinkman(HttpServletRequest request, HttpServletResponse response, String branchId) throws Exception {
		String fileName = excelService.generalLinkmanInfo(branchId);
		excelService.downloadExcel(response, fileName, excelPath);
	}
	
	@GetMapping("/expStorePj")
	@ApiOperation(value = "导出潽金库存")
	public void expStorePj(HttpServletRequest request, HttpServletResponse response, StorePjParam storePjParam) throws Exception {
		String fileName = excelService.generalGpsStorePj(storePjParam);
		excelService.downloadExcel(response, fileName, excelPath);
	}
	
	@GetMapping("/expStoreBranch")
	@ApiOperation(value = "导出经销商库存")
	public void expStoreBranch(HttpServletRequest request, HttpServletResponse response, GpsStoreBranchVo gpsStoreBranchVo) throws Exception {
		String fileName = excelService.generalGpsStoreBranch(gpsStoreBranchVo);
		excelService.downloadExcel(response, fileName, excelPath);
	}
}
