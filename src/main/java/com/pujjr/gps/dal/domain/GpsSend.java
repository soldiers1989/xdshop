package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gps_send")
public class GpsSend {
	/** 已经发货 */
	public final static int SEND_YES = 1;
	/** 未发货 */
	public final static int SEND_NOT = 0;

	/** gpsid长度限制 */
	public final static int GPSID_LENGTH = 32;
	/** 快递公司长度限制 */
	public final static int EXPRESS_COMPANY_LENGTH = 32;
	/** 快递编号长度限制 */
	public final static int EXPRESS_NO_LENGTH = 32;

	@Id
	private String sendId;

	private String detailId;

	private String brandId;

	private String outstoreId;

	private String gpsId;

	private String gpsCategory;

	private Date createTime;

	private Date sendTime;

	private String consigner;

	private Integer isSend;

	private String expressCompany;

	private String expressNo;

	private String supplierId;
	
	private String applyId;

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getOutstoreId() {
		return outstoreId;
	}

	public void setOutstoreId(String outstoreId) {
		this.outstoreId = outstoreId;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	public String getGpsCategory() {
		return gpsCategory;
	}

	public void setGpsCategory(String gpsCategory) {
		this.gpsCategory = gpsCategory;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getConsigner() {
		return consigner;
	}

	public void setConsigner(String consigner) {
		this.consigner = consigner;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
}