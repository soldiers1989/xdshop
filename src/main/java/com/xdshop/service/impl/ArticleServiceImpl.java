package com.xdshop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdshop.dal.dao.ArticleMapper;
import com.xdshop.dal.domain.Article;
import com.xdshop.service.IArticleService;
import com.xdshop.util.Utils;

@Service
public class ArticleServiceImpl implements IArticleService {
	private static final Logger logger = Logger.getLogger(ArticleServiceImpl.class);
	@Autowired
	private ArticleMapper articleMapper;
	@Override
	public Integer saveArticle(Article article) throws Exception {
		int updCount = 0;
		Article existArticle = articleMapper.selectByPublishId(article.getPublishId());
		if(existArticle == null){
			article.setId(Utils.get16UUID());
			updCount = articleMapper.insertSelective(article);
		}else{
			updCount = articleMapper.updateByPrimaryKeySelective(article);
		}
		return updCount;
	}
	
	@Override
	public Article getArticle(String publishId) throws Exception {
		return articleMapper.selectByPublishId(publishId);
	}
	
	

}
