package com.xdshop.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleVo {
	@XmlCDATA
	@XmlElement(name = "Title")
	private String Title;
	@XmlCDATA
	@XmlElement(name = "Description")
	private String Description;
	@XmlCDATA
	@XmlElement(name = "PicUrl")
	private String PicUrl;
	@XmlCDATA
	@XmlElement(name = "Url")
	private String Url;
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
	
	
}
