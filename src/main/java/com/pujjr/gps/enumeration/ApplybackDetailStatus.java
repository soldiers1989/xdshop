package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum ApplybackDetailStatus {

	/** 待收货 */
	WAITING("01", "待收货"),
	/** 已收货 */
	RECEIVED("02", "已收货"),
	/** 丢弃 */
	MISS("03", "库存丢失");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private ApplybackDetailStatus(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, ApplybackDetailStatus> codeMappingCache = new HashMap<String, ApplybackDetailStatus>();
	static {
		for (ApplybackDetailStatus type : ApplybackDetailStatus.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static ApplybackDetailStatus fromValue(String code) {
		return codeMappingCache.get(code);
	}

	public static void main(String[] args) {
		System.out.println(ApplybackDetailStatus.contains("03"));
	}
}
