package com.xdshop.vo;

import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;

public class PublishVo extends Publish{
	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
}
