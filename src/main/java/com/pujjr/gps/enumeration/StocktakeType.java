package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum StocktakeType {
	BATCH("batch", "批量"),
	SINGLE("single", "单笔");
	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private StocktakeType(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, StocktakeType> codeMappingCache = new HashMap<String, StocktakeType>();
	static {
		for (StocktakeType type : StocktakeType.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static StocktakeType fromValue(String code) {
		return codeMappingCache.get(code);
	}
}
