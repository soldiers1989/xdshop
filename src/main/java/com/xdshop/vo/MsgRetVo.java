package com.xdshop.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(propOrder = {"header","body"})
@XmlRootElement(name = "xml")
public class MsgRetVo {
	
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
	@XmlElement(name = "ArticleCount")
	private int ArticleCount;
	@XmlCDATA
	@XmlElement(name = "item")
	@XmlElementWrapper(name = "Articles")
	private List<ArticleVo> articles;
	
	
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
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<ArticleVo> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticleVo> articles) {
		this.articles = articles;
	}


}
