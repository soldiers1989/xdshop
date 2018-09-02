package com.xdshop.dal.dao;

import com.xdshop.dal.domain.Article;

public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);
    
    Article selectByPublishId(String publishId);
}