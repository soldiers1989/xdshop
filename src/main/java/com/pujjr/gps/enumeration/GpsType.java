package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum GpsType {

	WIRE("有线设备"),
	WIRELESS("无线设备");

	private String remark;

	public String getRemark() {
		return remark;
	}

	private GpsType(String remark) {
		this.remark = remark;
	}

	private static Map<String, GpsType> codeMappingCache = new HashMap<String, GpsType>();
	static {
		for (GpsType type : GpsType.values()) {
			codeMappingCache.put(type.name(), type);
		}
	}

	public static boolean contains(String name) {
		return codeMappingCache.get(name) != null;
	}

	public static GpsType fromValue(String name) {
		return codeMappingCache.get(name);
	}

}
