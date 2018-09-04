package com.xdshop.dal.dao;

import org.apache.ibatis.annotations.Param;

import com.xdshop.dal.domain.UserShare;

public interface UserShareMapper {
    int deleteByPrimaryKey(String id);

	int insert(UserShare record);

	int insertSelective(UserShare record);

	UserShare selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UserShare record);

	int updateByPrimaryKey(UserShare record);

	
    
    UserShare selectByPublishIdAndOpenId(@Param("publishId") String publishId,@Param("openId") String openId);

}