package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;

public interface IArticleService {
	
	public Integer saveArticle(Article article) throws Exception;
	
	public Article getArticle(String publishId) throws Exception;
	
}
