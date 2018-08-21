package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum OrderDetailStatus {

	/** 未提交 */
	WAIT_SEND("01", "待发货"),
	/** 待收货 */
	WAIT_RECEIVE("02", "待收货"),
	/** 已收货 */
	HAVA_RECEIVE("03", "已收货"),
	/** 已拒绝:订单审批被拒绝*/
	HAVA_REJECT("04", "已拒绝");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private OrderDetailStatus(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, OrderDetailStatus> codeMappingCache = new HashMap<String, OrderDetailStatus>();
	static {
		for (OrderDetailStatus type : OrderDetailStatus.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static OrderDetailStatus fromValue(String code) {
		return codeMappingCache.get(code);
	}

}
