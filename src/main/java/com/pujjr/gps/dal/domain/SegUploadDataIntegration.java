package com.pujjr.gps.dal.domain;

import java.util.List;

public class SegUploadDataIntegration {
	
	private List<SegUploadData> data;
	
	private String sign;//秘钥
	
	private String customerName;//客户姓名
	
	private String contractNo;//合同号
	
	private String callLetter;//GPS编号

	
	public String getCallLetter() {
		return callLetter;
	}

	public void setCallLetter(String callLetter) {
		this.callLetter = callLetter;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public List<SegUploadData> getData() {
		return data;
	}

	public void setData(List<SegUploadData> data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
