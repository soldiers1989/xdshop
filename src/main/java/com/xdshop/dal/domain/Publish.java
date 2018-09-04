package com.xdshop.dal.domain;

import java.util.Date;

public class Publish {
    private String id;

	private String title;

	private Float starNum;

	private Double priceInit;

	private Double priceNow;

	private Integer ticketTotal;

	private Integer ticketSale;

	private Integer ticketRemain;

	private String posterOssKey;

	private String posterUrl;

	private String publishRule;

	private String scenicDesc;

	private Date timeBegin;

	private Date timeEnd;

	private String createId;

	private Date createTime;

	private Boolean openFlag;

	private Integer subUserNum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Float getStarNum() {
		return starNum;
	}

	public void setStarNum(Float starNum) {
		this.starNum = starNum;
	}

	public Double getPriceInit() {
		return priceInit;
	}

	public void setPriceInit(Double priceInit) {
		this.priceInit = priceInit;
	}

	public Double getPriceNow() {
		return priceNow;
	}

	public void setPriceNow(Double priceNow) {
		this.priceNow = priceNow;
	}

	public Integer getTicketTotal() {
		return ticketTotal;
	}

	public void setTicketTotal(Integer ticketTotal) {
		this.ticketTotal = ticketTotal;
	}

	public Integer getTicketSale() {
		return ticketSale;
	}

	public void setTicketSale(Integer ticketSale) {
		this.ticketSale = ticketSale;
	}

	public Integer getTicketRemain() {
		return ticketRemain;
	}

	public void setTicketRemain(Integer ticketRemain) {
		this.ticketRemain = ticketRemain;
	}

	public String getPosterOssKey() {
		return posterOssKey;
	}

	public void setPosterOssKey(String posterOssKey) {
		this.posterOssKey = posterOssKey;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getPublishRule() {
		return publishRule;
	}

	public void setPublishRule(String publishRule) {
		this.publishRule = publishRule;
	}

	public String getScenicDesc() {
		return scenicDesc;
	}

	public void setScenicDesc(String scenicDesc) {
		this.scenicDesc = scenicDesc;
	}

	public Date getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(Date timeBegin) {
		this.timeBegin = timeBegin;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Boolean openFlag) {
		this.openFlag = openFlag;
	}

	public Integer getSubUserNum() {
		return subUserNum;
	}

	public void setSubUserNum(Integer subUserNum) {
		this.subUserNum = subUserNum;
	}

	



}