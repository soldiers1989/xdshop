package com.xdshop.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"header","body"})
@XmlRootElement(name = "xml")
public class MsgRcvVo {
	
	@XmlCDATA
	@XmlElement(name = "ToUserName")
	private String ToUserName;
	@XmlCDATA
	@XmlElement(name = "FromUserName")
	private String FromUserName;
	@XmlCDATA
	@XmlElement(name = "CreateTime")
	private long CreateTime;
	@XmlCDATA
	@XmlElement(name = "MsgType")
	private String MsgType;
	@XmlCDATA
	@XmlElement(name = "Content")
	private String Content;
	@XmlCDATA
	@XmlElement(name = "MsgId")
	private String MsgId;
	@XmlCDATA
	@XmlElement(name = "Event")
	private String Event;
	@XmlCDATA
	@XmlElement(name = "EventKey")
	private String EventKey;
	@XmlCDATA
	@XmlElement(name = "Ticket")
	private String Ticket;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
	
	
}
