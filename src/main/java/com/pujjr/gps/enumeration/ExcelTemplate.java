package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum ExcelTemplate {

	orderDetail("采购订单详情"),
	sendFeedback("发货反馈模板");

	private String remark;

	public String getRemark() {
		return remark;
	}

	private ExcelTemplate(String remark) {
		this.remark = remark;
	}

	private static Map<String, ExcelTemplate> codeMappingCache = new HashMap<String, ExcelTemplate>();
	static {
		for (ExcelTemplate type : ExcelTemplate.values()) {
			codeMappingCache.put(type.name(), type);
		}
	}

	public static boolean contains(String name) {
		return codeMappingCache.get(name) != null;
	}

	public static ExcelTemplate fromValue(String name) {
		return codeMappingCache.get(name);
	}

	public static String nameToRemark(String name) {
		ExcelTemplate xlsxTemplate = fromValue(name);
		return xlsxTemplate.getRemark();
	}

	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<>();
		ExcelTemplate[] excelTemplateArray = ExcelTemplate.values();
		for (ExcelTemplate excelTemplate : excelTemplateArray) {
			map.put(excelTemplate.name(), excelTemplate.getRemark());
		}
		return map;
	}
}
