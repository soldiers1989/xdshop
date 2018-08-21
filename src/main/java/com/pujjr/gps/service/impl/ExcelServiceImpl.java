package com.pujjr.gps.service.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.pujjr.gps.api.GpsLinkmanParam;
import com.pujjr.gps.api.StocktakeParam;
import com.pujjr.gps.api.StorePjParam;
import com.pujjr.gps.dal.dao.GpsLinkmanMapper;
import com.pujjr.gps.dal.dao.GpsStocktakeMapper;
import com.pujjr.gps.dal.dao.GpsStorePjMapper;
import com.pujjr.gps.dal.domain.GpsApply;
import com.pujjr.gps.dal.domain.GpsLinkman;
import com.pujjr.gps.dal.domain.GpsOrder;
import com.pujjr.gps.dal.domain.GpsOrderDetail;
import com.pujjr.gps.dal.domain.GpsStocktake;
import com.pujjr.gps.dal.domain.GpsSupplier;
import com.pujjr.gps.enumeration.ExcelTemplate;
import com.pujjr.gps.enumeration.OrderStatus;
import com.pujjr.gps.service.ApplyService;
import com.pujjr.gps.service.ExcelService;
import com.pujjr.gps.service.OrderService;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.util.Utils;
import com.pujjr.gps.vo.GpsStoreBranchVo;
import com.pujjr.gps.vo.GpsStorePjVo;
import com.pujjr.gps.vo.OrderDetailVO;
import com.pujjr.gps.vo.SendFeedbackVO;
import com.pujjr.gps.vo.SendGpsDetailVO;

/**
 * 申请单服务
 * 
 * @author wen
 * @date 创建时间：2017年10月10日 上午10:47:53
 *
 */
@Service
public class ExcelServiceImpl extends BaseService<ExcelService> implements ExcelService {

	private static final int ORDER_MAX_CELL_INDEX = 8;

	private static final int DEFAULT_ROW_HEIGHT_IN_POINTS = 25;

	private static final String TEMPLATE_PATH = "/template/";

	private static final String XLSX = ".xlsx";

	private static final int ORDER_ROW_INDEX = 1;

	private static final int SUPPLY_ROW_INDEX = 2;

	private static final int ORDER_SUPPLY_CELL_INDEX = 1;

	private static final int DATA_START_ROW_INDEX = 4;

	@Autowired
	ApplyService applyService;

	@Autowired
	OrderService orderService;
	@Autowired
	private GpsStocktakeMapper gpsStocktakeMapper;
	@Autowired
	private GpsLinkmanMapper gpsLinkmanMapper;
	@Autowired
	private GpsStorePjMapper gpsStorePjMapper;
	@Autowired
	private StoreServiceImpl storeServiceImpl;
	
	@Value("${file.excelPath}")
	private String excelPath;
	
	@Override
	public void downloadExcel(HttpServletResponse response,String fileName,String excelPath) throws Exception {
		FileInputStream fis = new FileInputStream(new File(excelPath +File.separator + fileName));
		byte[] bytes = new byte[1024];
		int length = 0;
		OutputStream os = response.getOutputStream();
		response.setContentType("application/x-msdownload");
		fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + fileName);
		while((length = fis.read(bytes,0,bytes.length)) > 0){
			os.write(bytes, 0, length);
		}
		os.close();
		File file = new File(excelPath + File.separator +fileName);
		file.delete();
	}
	

	@Override
	public byte[] getExcelTemplate(ExcelTemplate template) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = getExcelTemplateInputStream(template);
			return IOUtils.toByteArray(inputStream);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * @param template
	 * @return
	 */
	public InputStream getExcelTemplateInputStream(ExcelTemplate template) {
		InputStream inputStream = this.getClass().getResourceAsStream(TEMPLATE_PATH + template.name() + XLSX);
		Assert.notNull(inputStream, "Excel模板读取失败");
		return inputStream;
	}

	@Override
	public String getFileName(ExcelTemplate template) {
		return template.getRemark() + XLSX;
	}

	public List<OrderDetailVO> getOrderDetailExcelData(String orderId) {
		List<OrderDetailVO> list = new ArrayList<>();
		// 获取订单明细
		List<GpsOrderDetail> gpsOrderDetailList = orderService.selectOrderDetailListByOrderId(orderId);
		for (GpsOrderDetail gpsOrderDetail : gpsOrderDetailList) {
			OrderDetailVO orderDetailVO = new OrderDetailVO();
			// 获取订单详情相关
			GpsApply gpsApply = applyService.selectBaseApplyByApplyId(gpsOrderDetail.getApplyId());
			// 获取联系人
			GpsLinkman gpsLinkman = applyService.selectLinkmanByLinkmanId(gpsApply.getLinkmanId());
			// 填值
			orderDetailVO.setDetailId(gpsOrderDetail.getDetailId());
			orderDetailVO.setBranchName(gpsOrderDetail.getBranchName());
			orderDetailVO.setWireNum(gpsOrderDetail.getWireNum());
			orderDetailVO.setWirelessNum(gpsOrderDetail.getWirelessNum());
//			orderDetailVO.setAddress(StringUtils.join(gpsLinkman.getAddrProvince(), gpsLinkman.getAddrCity(), gpsLinkman.getAddrDistrict(), gpsLinkman.getAddrDetail()));
			try{
				orderDetailVO.setAddress(gpsLinkman.getAddrProvinceName() 
						+ " "
						+ gpsLinkman.getAddrCityName() 
						+ " " 
						+ gpsLinkman.getAddrDistrictName()
						+ " "
						+ gpsLinkman.getAddrDetail());
				orderDetailVO.setName(gpsLinkman.getName());
				orderDetailVO.setMobile1(gpsLinkman.getMobile1());
			}catch(Exception e){
				/**
				 * 申请单提交时，无收货人信息，gps管理员在发货时，导出订单明细后，线下联系处理
				 */
				orderDetailVO.setAddress("");
				orderDetailVO.setName("");
				orderDetailVO.setMobile1("");
			}
			
			orderDetailVO.setRemark(gpsApply.getRemark());
			// 存入
			list.add(orderDetailVO);
		}
		return list;
	}

	@Override
	public byte[] exportOrderDetail(String orderId) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 获取供应商
		GpsOrder gpsOrder = orderService.selectByOrderId(orderId);
		Assert.isTrue(StringUtils.equals(gpsOrder.getOrderStatus(), OrderStatus.APPROVE_PASS.getCode()), "只能导出审批通过订单的明细");
		List<OrderDetailVO> orderDetailVOs = getOrderDetailExcelData(orderId);
		InputStream inputStream = getExcelTemplateInputStream(ExcelTemplate.orderDetail);
		// 导出为Excel
		// 创建一个WorkBook，从指定的文件流中创建，即上面指定了的文件流
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			// 默认高度
			sheet.setDefaultRowHeightInPoints(DEFAULT_ROW_HEIGHT_IN_POINTS);
			// 设置样式
			XSSFCellStyle style = getDefaultStyle(workbook);
			// 设置订单编号
			XSSFRow orderIdRow = sheet.getRow(ORDER_ROW_INDEX);
			setCellValue(orderId, style, orderIdRow, ORDER_SUPPLY_CELL_INDEX);
			// 设置供应商
			GpsSupplier supplier = gpsOrder.getGpsSupplier();
			XSSFRow supplierCodeRow = sheet.getRow(SUPPLY_ROW_INDEX);
			setCellValue(supplier.getSupplierName(), style, supplierCodeRow, ORDER_SUPPLY_CELL_INDEX);
			// 填入数据
			for (int indexRow = 0; orderDetailVOs != null && indexRow < orderDetailVOs.size(); indexRow++) {
				OrderDetailVO orderDetailVO = orderDetailVOs.get(indexRow);
				XSSFRow newRow = sheet.createRow(indexRow + DATA_START_ROW_INDEX);
				int cellIndex = 0;
				setCellValue(orderDetailVO.getDetailId(), style, newRow, cellIndex++);
				setCellValue(orderDetailVO.getBranchName(), style, newRow, cellIndex++);
				setCellValue(String.valueOf(orderDetailVO.getWireNum()), style, newRow, cellIndex++);
				setCellValue(String.valueOf(orderDetailVO.getWirelessNum()), style, newRow, cellIndex++);
				setCellValue(orderDetailVO.getAddress(), style, newRow, cellIndex++);
				setCellValue(orderDetailVO.getName(), style, newRow, cellIndex++);
				setCellValue(orderDetailVO.getMobile1(), style, newRow, cellIndex++);
				setCellValue(orderDetailVO.getRemark(), style, newRow, cellIndex++);
			}
			autoAdjustWidthColumn(workbook, ORDER_MAX_CELL_INDEX);
			// 输出文件
			workbook.write(byteArrayOutputStream);
			return byteArrayOutputStream.toByteArray();
		} finally {
			// 关闭流
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(workbook);
		}
	}

	@Override
	public SendFeedbackVO importSendFeedback(InputStream inputStream) throws Exception {
		Assert.notNull(inputStream, "Excel读取失败");
		// 创建一个WorkBook，从指定的文件流中创建，即上面指定了的文件流
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		// 建立存放数据
		SendFeedbackVO sendFeedback = new SendFeedbackVO();
		try {
			// 注意，如果不能确定具体的名称，可以用getSheetAt(int)方法取得Sheet
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			if (sheet == null) {
				sheet = workbook.getSheetAt(0);
			}

			Assert.isTrue((sheet.getPhysicalNumberOfRows() != 0), "所选工作表为空表");

			// 建立x列坐标记录与y行坐标记录
			int rowIndex = 0;

			List<SendGpsDetailVO> sendGpsDetailList = new ArrayList<>();
			// 循环获取所有行
			for (; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
				// 获得单条行
				XSSFRow row = sheet.getRow(rowIndex);
//				Assert.notNull(row, "第" + (rowIndex + 1) + "行为空数据行,可能存在空格,请删除掉该行再重新尝试");
				if(row != null){
					switch (rowIndex) {
					case ORDER_ROW_INDEX:
						// 订单编号
						sendFeedback.setOrderId(getCellValue(row, ORDER_SUPPLY_CELL_INDEX));
						break;
					case SUPPLY_ROW_INDEX:
						// 供应商
						sendFeedback.setSupplyName(getCellValue(row, ORDER_SUPPLY_CELL_INDEX));
						break;
					default:
						if (rowIndex >= DATA_START_ROW_INDEX) {
							// 循环获取单元格值
							SendGpsDetailVO sendGpsDetail = rowToSendFeedback(row);
							if(sendGpsDetail.getDetailId() != null){
								if(!"".equals(sendGpsDetail.getDetailId().trim())){
									sendGpsDetailList.add(sendGpsDetail);
								}
							}
						}
						break;
					}
					sendFeedback.setSendGpsDetailList(sendGpsDetailList);
				}
			}
		} finally {
			// 关闭流
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(workbook);
		}

		return sendFeedback;
	}

	private SendGpsDetailVO rowToSendFeedback(XSSFRow row) {
		int cellIndex = 0;
		SendGpsDetailVO sendGpsDetail = new SendGpsDetailVO();
		// 设备编号 设备类型 设备品牌 快递公司 快递单号
		sendGpsDetail.setDetailId(getCellValue(row, cellIndex++));
		sendGpsDetail.setBranchName(getCellValue(row, cellIndex++));
		sendGpsDetail.setGpsId(getCellValue(row, cellIndex++));
		sendGpsDetail.setGpsCategory(getCellValue(row, cellIndex++));
		sendGpsDetail.setBrandName(getCellValue(row, cellIndex++));
		sendGpsDetail.setExpressCompany(getCellValue(row, cellIndex++));
		sendGpsDetail.setExpressNo(getCellValue(row, cellIndex++));
		return sendGpsDetail;
	}

	/**
	 * @param row
	 * @param cellIndex
	 * @return
	 */
	public String getCellValue(XSSFRow row, int cellIndex) {
		XSSFCell cell = row.getCell((short) cellIndex);
		String cellValue = "";
		if (cell != null) {
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cellValue = cell.getRichStringCellValue().getString();
			cellValue = cellValue.trim();
		}
		return cellValue;
	}

	/**
	 * @param orderId
	 * @param style
	 * @param orderIdRow
	 */
	public void setCellValue(String value, XSSFCellStyle style, XSSFRow row, int index) {
		XSSFCell cell = row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	public static XSSFCellStyle getDefaultStyle(XSSFWorkbook wb) {
		// 设置样式
		XSSFCellStyle style = wb.createCellStyle();
		// 自动换行
		style.setWrapText(true);
		style.setBorderBottom((short) 1);
		style.setBorderTop((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);

		// 垂直  
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 水平  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		XSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("仿宋");
		style.setFont(font);
		return style;
	}

	/**
	 * 自动调节行宽
	 * 
	 * @param sheet
	 * @param n
	 */
	private static void autoAdjustWidthColumn(XSSFWorkbook workbook, int n) {
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			XSSFSheet sheet = workbook.getSheetAt(i);
			for (int j = 0; j < n; j++) {
				// 调整第一行宽度
				sheet.autoSizeColumn(j);
			}
		}
	}

	public XSSFCellStyle getTitleStyle(SXSSFWorkbook workBook){
		XSSFCellStyle cellStyle = (XSSFCellStyle) workBook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
//		cellStyle.setFillBackgroundColor(new XSSFColor(new Color(100, 100, 100)));
		XSSFFont font = (XSSFFont) workBook.createFont();
		font.setFontHeight(20);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public XSSFCellStyle getColNameStyle(SXSSFWorkbook workBook){
		XSSFCellStyle cellStyle = (XSSFCellStyle) workBook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(208, 206, 206)));
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setFillBackgroundColor(new XSSFColor(new Color(100, 100, 100)));
		XSSFFont font = (XSSFFont) workBook.createFont();
		font.setFontHeight(12);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	public XSSFCellStyle getContentStyle(SXSSFWorkbook workBook){
		XSSFCellStyle cellStyle = (XSSFCellStyle) workBook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(new XSSFColor(new Color(255, 255, 255)));
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
//		cellStyle.setFillBackgroundColor(new XSSFColor(new Color(100, 100, 100)));
		XSSFFont font = (XSSFFont) workBook.createFont();
		font.setFontHeight(10);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	/*public void setCellValue(SXSSFRow tempRow,Cell tempCell,String value,XSSFCellStyle cellStyle){
		Cell tempCell0 = tempRow.createCell(0);
		tempCell0.setCellValue(value);
		tempCell0.setCellStyle(cellStyle);
	}*/
	@Override
	public String generalStocktakeInfo(StocktakeParam stocktakeParam) throws Exception {
		String title = "库存盘点信息";
		//列名
		String[] cols = new String[]{"盘点编号","本月使用量（有线）","本月使用量（无线）","当前库存量（有线）","当前库存量（无线）","盘点申请量（有线）","盘点申请量（无线）","经销商编号","经销商名称","盘点日期"};
		int currRow = 0;
		String fileName = title + ".xlsx";
		byte[] byteArray = null;
		
		SXSSFWorkbook workBook = new SXSSFWorkbook(new XSSFWorkbook());
		XSSFCellStyle titleStyle = this.getTitleStyle(workBook);
		XSSFCellStyle colNameStyle = this.getColNameStyle(workBook);
		
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet();
		SXSSFRow row = (SXSSFRow) sheet.createRow(currRow++);
		row.setHeight((short)1000);
		for (int i = 0; i < cols.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			if(i == 0){
				cell.setCellValue(title);
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols.length - 1));
		
		//表格头
		SXSSFRow row1 = (SXSSFRow) sheet.createRow(currRow++);
		row1.setHeight((short)500);
		for (int i = 0; i < cols.length; i++) {
			Cell tempCell = row1.createCell(i);
			tempCell.setCellValue(cols[i]);
			tempCell.setCellStyle(colNameStyle);
			if(i == 8){
				sheet.setColumnWidth(i, 8000);
			}else{
				sheet.setColumnWidth(i, 6000);
			}
		}
		
		//表格体
		XSSFCellStyle contentStyle = this.getContentStyle(workBook);
		List<GpsStocktake> gpsStocktakeList = gpsStocktakeMapper.selectGpsStoreTake(stocktakeParam);
		for (int i = 0; i < gpsStocktakeList.size(); i++) {
			GpsStocktake tempStocktake = gpsStocktakeList.get(i);
			SXSSFRow tempRow = (SXSSFRow) sheet.createRow(currRow++);
			Cell tempCell0 = tempRow.createCell(0);
			tempCell0.setCellValue(tempStocktake.getBranchId());
			tempCell0.setCellStyle(contentStyle);
			Cell tempCell1 = tempRow.createCell(1);
			tempCell1.setCellValue(tempStocktake.getUsedWireNum());
			tempCell1.setCellStyle(contentStyle);
			Cell tempCell2 = tempRow.createCell(2);
			tempCell2.setCellValue(tempStocktake.getUsedWirelessNum());
			tempCell2.setCellStyle(contentStyle);
			Cell tempCell3 = tempRow.createCell(3);
			tempCell3.setCellValue(tempStocktake.getTotalWireNum());
			tempCell3.setCellStyle(contentStyle);
			Cell tempCell4 = tempRow.createCell(4);
			tempCell4.setCellValue(tempStocktake.getTotalWirelessNum());
			tempCell4.setCellStyle(contentStyle);
			Cell tempCell5 = tempRow.createCell(5);
			tempCell5.setCellValue(tempStocktake.getApplyWireNum());
			tempCell5.setCellStyle(contentStyle);
			Cell tempCell6 = tempRow.createCell(6);
			tempCell6.setCellValue(tempStocktake.getApplyWirelessNum());
			tempCell6.setCellStyle(contentStyle);
			Cell tempCell7 = tempRow.createCell(7);
			tempCell7.setCellValue(tempStocktake.getBranchId());
			tempCell7.setCellStyle(contentStyle);
			Cell tempCell8 = tempRow.createCell(8);
			tempCell8.setCellValue(tempStocktake.getBranchName());
			tempCell8.setCellStyle(contentStyle);
			Cell tempCell9 = tempRow.createCell(9);
			tempCell9.setCellValue(Utils.formateDate2String(tempStocktake.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			tempCell9.setCellStyle(contentStyle);
		}
		FileOutputStream fos = new FileOutputStream(excelPath+File.separator+fileName);
		workBook.write(fos);
		fos.close();
		workBook.close();
		return fileName;
	}


	@Override
	public String generalLinkmanInfo(String branchId) throws Exception {
		String title = "联系人信息";
		//列名
		String[] cols = new String[]{"姓名","所属机构","联系人地址","联系人电话1","联系人电话2","是否默认联系人"};
		int currRow = 0;
		String fileName = title + ".xlsx";
		byte[] byteArray = null;
		
		SXSSFWorkbook workBook = new SXSSFWorkbook(new XSSFWorkbook());
		XSSFCellStyle titleStyle = this.getTitleStyle(workBook);
		XSSFCellStyle colNameStyle = this.getColNameStyle(workBook);
		
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet();
		SXSSFRow row = (SXSSFRow) sheet.createRow(currRow++);
		row.setHeight((short)1000);
		for (int i = 0; i < cols.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			if(i == 0){
				cell.setCellValue(title);
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols.length - 1));
		
		//表格头
		SXSSFRow row1 = (SXSSFRow) sheet.createRow(currRow++);
		row1.setHeight((short)500);
		for (int i = 0; i < cols.length; i++) {
			Cell tempCell = row1.createCell(i);
			tempCell.setCellValue(cols[i]);
			tempCell.setCellStyle(colNameStyle);
			if(i == 1 || i == 2){
				sheet.setColumnWidth(i, 8000);
			}else{
				sheet.setColumnWidth(i, 6000);
			}
		}
		
		//表格体
		XSSFCellStyle contentStyle = this.getContentStyle(workBook);
		GpsLinkmanParam gpsLinkmanParam = new GpsLinkmanParam();
		gpsLinkmanParam.setBranchId(branchId);
		List<GpsLinkman> gpsLinkmanList = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam);
		
//		List<GpsStocktake> gpsStocktakeList = gpsStocktakeMapper.selectGpsStoreTake(stocktakeParam);
		for (int i = 0; i < gpsLinkmanList.size(); i++) {
			GpsLinkman gpsLinkman = gpsLinkmanList.get(i);
			SXSSFRow tempRow = (SXSSFRow) sheet.createRow(currRow++);
			Cell tempCell0 = tempRow.createCell(0);
			tempCell0.setCellValue(gpsLinkman.getName());
			tempCell0.setCellStyle(contentStyle);
			Cell tempCell1 = tempRow.createCell(1);
			tempCell1.setCellValue(gpsLinkman.getBranchName());
			tempCell1.setCellStyle(contentStyle);
			Cell tempCell2 = tempRow.createCell(2);
			tempCell2.setCellValue(gpsLinkman.getAddrProvinceName() + " " + gpsLinkman.getAddrCityName() + " " +gpsLinkman.getAddrDistrictName() + " "+gpsLinkman.getAddrDetail());
			tempCell2.setCellStyle(contentStyle);
			Cell tempCell3 = tempRow.createCell(3);
			tempCell3.setCellValue(gpsLinkman.getMobile1());
			tempCell3.setCellStyle(contentStyle);
			Cell tempCell4 = tempRow.createCell(4);
			tempCell4.setCellValue(gpsLinkman.getMobile2());
			tempCell4.setCellStyle(contentStyle);
			Cell tempCell5 = tempRow.createCell(5);
			tempCell5.setCellValue(gpsLinkman.getIsDefault()? "是" : "否");
			tempCell5.setCellStyle(contentStyle);
		}
		FileOutputStream fos = new FileOutputStream(excelPath+File.separator+fileName);
		workBook.write(fos);
		fos.close();
		workBook.close();
		return fileName;
	}


	@Override
	public String generalGpsStorePj(StorePjParam storePjParam) throws Exception {
		String title = "潽金库存信息";
		//列名
		String[] cols = new String[]{"GPS品牌","编码","类型","状态"};
		int currRow = 0;
		String fileName = title + ".xlsx";
		byte[] byteArray = null;
		
		SXSSFWorkbook workBook = new SXSSFWorkbook(new XSSFWorkbook());
		XSSFCellStyle titleStyle = this.getTitleStyle(workBook);
		XSSFCellStyle colNameStyle = this.getColNameStyle(workBook);
		
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet();
		SXSSFRow row = (SXSSFRow) sheet.createRow(currRow++);
		row.setHeight((short)1000);
		for (int i = 0; i < cols.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			if(i == 0){
				cell.setCellValue(title);
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols.length - 1));
		
		//表格头
		SXSSFRow row1 = (SXSSFRow) sheet.createRow(currRow++);
		row1.setHeight((short)500);
		for (int i = 0; i < cols.length; i++) {
			Cell tempCell = row1.createCell(i);
			tempCell.setCellValue(cols[i]);
			tempCell.setCellStyle(colNameStyle);
			if(i == 1 || i == 2){
				sheet.setColumnWidth(i, 8000);
			}else{
				sheet.setColumnWidth(i, 6000);
			}
		}
		
		//表格体
		XSSFCellStyle contentStyle = this.getContentStyle(workBook);
//		GpsLinkmanParam gpsLinkmanParam = new GpsLinkmanParam();
//		gpsLinkmanParam.setBranchId(branchId);
//		List<GpsLinkman> gpsLinkmanList = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam);
		List<GpsStorePjVo> gpsStorePjList = storeServiceImpl.getAllPjStore(storePjParam);
//		List<GpsStocktake> gpsStocktakeList = gpsStocktakeMapper.selectGpsStoreTake(stocktakeParam);
		for (int i = 0; i < gpsStorePjList.size(); i++) {
			GpsStorePjVo gpsStorePj = gpsStorePjList.get(i);
			SXSSFRow tempRow = (SXSSFRow) sheet.createRow(currRow++);
			Cell tempCell0 = tempRow.createCell(0);
			tempCell0.setCellValue(gpsStorePj.getBrandName());
			tempCell0.setCellStyle(contentStyle);
			Cell tempCell1 = tempRow.createCell(1);
			tempCell1.setCellValue(gpsStorePj.getGpsId());
			tempCell1.setCellStyle(contentStyle);
			Cell tempCell2 = tempRow.createCell(2);
			tempCell2.setCellValue(gpsStorePj.getGpsCategoryName());
			tempCell2.setCellStyle(contentStyle);
			Cell tempCell3 = tempRow.createCell(3);
			tempCell3.setCellValue(gpsStorePj.getGpsStatusName());
			tempCell3.setCellStyle(contentStyle);
		}
		FileOutputStream fos = new FileOutputStream(excelPath+File.separator+fileName);
		workBook.write(fos);
		fos.close();
		workBook.close();
		return fileName;
	}


	@Override
	public String generalGpsStoreBranch(GpsStoreBranchVo gpsStoreBranchVo) throws Exception {
		String title = "经销商库存信息";
		//列名
		String[] cols = new String[]{"经销商名称","供应商名称","品牌","编码","类型","状态"};
		int currRow = 0;
		String fileName = title + ".xlsx";
		byte[] byteArray = null;
		
		SXSSFWorkbook workBook = new SXSSFWorkbook(new XSSFWorkbook());
		XSSFCellStyle titleStyle = this.getTitleStyle(workBook);
		XSSFCellStyle colNameStyle = this.getColNameStyle(workBook);
		
		SXSSFSheet sheet = (SXSSFSheet) workBook.createSheet();
		SXSSFRow row = (SXSSFRow) sheet.createRow(currRow++);
		row.setHeight((short)1000);
		for (int i = 0; i < cols.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellStyle(titleStyle);
			if(i == 0){
				cell.setCellValue(title);
			}
		}
		
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cols.length - 1));
		
		//表格头
		SXSSFRow row1 = (SXSSFRow) sheet.createRow(currRow++);
		row1.setHeight((short)500);
		for (int i = 0; i < cols.length; i++) {
			Cell tempCell = row1.createCell(i);
			tempCell.setCellValue(cols[i]);
			tempCell.setCellStyle(colNameStyle);
			if(i == 0){
				sheet.setColumnWidth(i, 12000);
			}else{
				sheet.setColumnWidth(i, 6000);
			}
		}
		
		//表格体
		XSSFCellStyle contentStyle = this.getContentStyle(workBook);
//		GpsLinkmanParam gpsLinkmanParam = new GpsLinkmanParam();
//		gpsLinkmanParam.setBranchId(branchId);
//		List<GpsLinkman> gpsLinkmanList = gpsLinkmanMapper.selectGpsLinkmanByParam(gpsLinkmanParam);
		List<GpsStoreBranchVo> gpsStorePjList = storeServiceImpl.getAllBranchStore(gpsStoreBranchVo);
//		List<GpsStocktake> gpsStocktakeList = gpsStocktakeMapper.selectGpsStoreTake(stocktakeParam);
		for (int i = 0; i < gpsStorePjList.size(); i++) {
			GpsStoreBranchVo gpsStoreBranch = gpsStorePjList.get(i);
			SXSSFRow tempRow = (SXSSFRow) sheet.createRow(currRow++);
			
			Cell tempCell0 = tempRow.createCell(0);
			tempCell0.setCellValue(gpsStoreBranch.getBranchName());
			tempCell0.setCellStyle(contentStyle);
			Cell tempCell1 = tempRow.createCell(1);
			tempCell1.setCellValue(gpsStoreBranch.getSupplierName());
			tempCell1.setCellStyle(contentStyle);
			Cell tempCell2 = tempRow.createCell(2);
			tempCell2.setCellValue(gpsStoreBranch.getBrandName());
			tempCell2.setCellStyle(contentStyle);
			Cell tempCell3 = tempRow.createCell(3);
			tempCell3.setCellValue(gpsStoreBranch.getGpsId());
			tempCell3.setCellStyle(contentStyle);
			Cell tempCell4 = tempRow.createCell(4);
			tempCell4.setCellValue(gpsStoreBranch.getGpsCategoryName());
			tempCell4.setCellStyle(contentStyle);
			Cell tempCell5 = tempRow.createCell(5);
			tempCell5.setCellValue(gpsStoreBranch.getGpsStatusName());
			tempCell5.setCellStyle(contentStyle);
		}
		FileOutputStream fos = new FileOutputStream(excelPath+File.separator+fileName);
		workBook.write(fos);
		fos.close();
		workBook.close();
		return fileName;
	}
}
