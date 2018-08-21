package com.pujjr.gps.enumeration;

import java.util.HashMap;
import java.util.Map;

/**
 * 审核审批节点
 * 
 * @author wen
 * @date 创建时间：2017年7月13日 下午4:15:44
 *
 */
public enum CheckhisNode {

	/** 申请单提交 */
	APPLY_SUBMITTED("00", "申请单提交"),
	/** 申请单审核 */
	APPLY_CHECK("01", "申请单审核"),
	/** 申请单审批 */
	APPLY_APPROVE("02", "申请单审批"),
	/** 订单提交 */
	ORDER_SUBMITTED("03", "订单提交"),
	/** 订单审批 */
	ORDER_APPROVE("04", "订单审批");

	private String code;

	private String remark;

	public String getCode() {
		return code;
	}

	public String getRemark() {
		return remark;
	}

	private CheckhisNode(String code, String remark) {
		this.code = code;
		this.remark = remark;
	}

	private static Map<String, CheckhisNode> codeMappingCache = new HashMap<String, CheckhisNode>();
	static {
		for (CheckhisNode type : CheckhisNode.values()) {
			codeMappingCache.put(type.getCode(), type);
		}
	}

	public static boolean contains(String code) {
		return codeMappingCache.get(code) != null;
	}

	public static CheckhisNode fromValue(String code) {
		return codeMappingCache.get(code);
	}

}
