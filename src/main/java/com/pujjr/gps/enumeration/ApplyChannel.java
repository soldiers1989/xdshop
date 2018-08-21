package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum ApplyChannel {

	PUJJR("潽金"),
	ICBC("工行");

	private String remark;

	public String getRemark() {
		return remark;
	}

	private ApplyChannel(String remark) {
		this.remark = remark;
	}

	private static Map<String, ApplyChannel> codeMappingCache = new HashMap<String, ApplyChannel>();
	static {
		for (ApplyChannel type : ApplyChannel.values()) {
			codeMappingCache.put(type.name(), type);
		}
	}

	public static boolean contains(String name) {
		return codeMappingCache.get(name) != null;
	}

	public static ApplyChannel fromValue(String name) {
		return codeMappingCache.get(name);
	}
}
