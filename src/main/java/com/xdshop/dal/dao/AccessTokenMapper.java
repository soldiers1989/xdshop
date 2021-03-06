package com.xdshop.dal.dao;

import com.xdshop.dal.domain.AccessToken;

public interface AccessTokenMapper {
    int deleteByPrimaryKey(String id);

    int insert(AccessToken record);

    int insertSelective(AccessToken record);

    AccessToken selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AccessToken record);

    int updateByPrimaryKey(AccessToken record);
}