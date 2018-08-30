package com.xdshop.api;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

public class BaseParam extends QueryParamPageVo{
	@ApiModelProperty(value = "起始日期")
	private Date timeBegin;
	@ApiModelProperty(value = "截止日期")
	private Date timeEnd;
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
}
