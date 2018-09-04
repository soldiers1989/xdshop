package com.xdshop.dal.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdshop.api.PublishUserParam;
import com.xdshop.dal.domain.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    
    
    User selectByOpenId(String openId);
    
    List<User> selectByParentOpenId(String parentOpenId);
    
    List<HashMap<String,Object>> selectFetchUser(String publisId);
    
    List<HashMap<String,Object>> selectPublishUser(@Param("param") PublishUserParam param);
}