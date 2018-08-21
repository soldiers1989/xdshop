package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum OrderStatus {

	/** 未提交 */
	UNCOMMITTED("01", "未提交"),
	/** 已提交 */
	SUBMITTED("02", "已提交"),
	/** 审核通过 */
	CHECK_PASS("03", "审核通过"),
	/** 审核拒绝 */
	CHECK_REJECT("04", "审核拒绝"),
	/** 审批通过 */
	APPROVE_PASS("05", "审批通过"),
	/** 审批拒绝 */
	APPROVE_REJECT("06", "审批拒绝"),
	WAIT_SEND("07", "待发货"),
	WAIT_RECEIVE("08", "待收货"),
	HAVE_RECEIVE("09", "已收货");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private OrderStatus(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, OrderStatus> codeMappingCache = new HashMap<String, OrderStatus>();
	static {
		for (OrderStatus type : OrderStatus.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static OrderStatus fromValue(String code) {
		return codeMappingCache.get(code);
	}

}
