package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum ApplyType {

	AUTO("自动"),
	MANUAL("人工");

	private String remark;

	public String getRemark() {
		return remark;
	}

	private ApplyType(String remark) {
		this.remark = remark;
	}

	private static Map<String, ApplyType> codeMappingCache = new HashMap<String, ApplyType>();
	static {
		for (ApplyType type : ApplyType.values()) {
			codeMappingCache.put(type.name(), type);
		}
	}

	public static boolean contains(String name) {
		return codeMappingCache.get(name) != null;
	}

	public static ApplyType fromValue(String name) {
		return codeMappingCache.get(name);
	}
}
