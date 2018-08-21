package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum ApplybackStatus {

	/** 未提交 */
	UNCOMMITTED("01", "未提交"),
	/** 已提交 */
	SUBMITTED("02", "已提交"),
	/** 全部确认 */
	DONE("03", "全部确认");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private ApplybackStatus(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, ApplybackStatus> codeMappingCache = new HashMap<String, ApplybackStatus>();
	static {
		for (ApplybackStatus type : ApplybackStatus.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static ApplybackStatus fromValue(String code) {
		return codeMappingCache.get(code);
	}

	public static void main(String[] args) {
		System.out.println(ApplybackStatus.contains("03"));
	}
}
