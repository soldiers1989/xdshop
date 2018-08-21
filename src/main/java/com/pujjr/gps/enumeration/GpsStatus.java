package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum GpsStatus {

	WAIT_RECEIVE("100", "待收货"),
	HAVE_RECEIVE("101", "已收货"),
	REJECT_RECEIVE("102", "拒收"),

	STORE_FREE("200", "空闲"),
	STORE_LOCK("201", "锁定"),
	STORE_INSTALLED("202", "已安装"),
	STORE_DISABLE("203", "禁用");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private GpsStatus(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, GpsStatus> codeMappingCache = new HashMap<String, GpsStatus>();
	static {
		for (GpsStatus type : GpsStatus.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
		for (GpsStatus type : GpsStatus.values()) {
			codeMappingCache.put(type.getRemark(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static GpsStatus fromValue(String code) {
		return codeMappingCache.get(code);
	}
	
	public static GpsStatus fromRemark(String remark) {
		return codeMappingCache.get(remark);
	}

	public static void main(String[] args) {
		System.out.println(GpsStatus.contains("03"));
	}
}
