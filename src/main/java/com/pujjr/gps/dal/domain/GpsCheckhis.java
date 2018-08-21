package com.pujjr.gps.dal.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gps_checkhis")
public class GpsCheckhis {

	public final static int COMMENT_LENGTH = 32;

	@Id
	private String id;

	private String pubId;

	private String nodeId;

	private Integer wireNum;

	private Integer wirelessNum;

	private String pubStatus;

	private String comment;

	private String createAccountId;

	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getWireNum() {
		return wireNum;
	}

	public void setWireNum(Integer wireNum) {
		this.wireNum = wireNum;
	}

	public Integer getWirelessNum() {
		return wirelessNum;
	}

	public void setWirelessNum(Integer wirelessNum) {
		this.wirelessNum = wirelessNum;
	}

	public String getPubStatus() {
		return pubStatus;
	}

	public void setPubStatus(String pubStatus) {
		this.pubStatus = pubStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreateAccountId() {
		return createAccountId;
	}

	public void setCreateAccountId(String createAccountId) {
		this.createAccountId = createAccountId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}