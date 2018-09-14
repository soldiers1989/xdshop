package com.xdshop.dal.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdshop.api.FetchUserParam;
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
    
    List<HashMap<String,Object>> selectFetchUser(@Param("param") FetchUserParam param);
    
    List<HashMap<String,Object>> selectPublishUser(@Param("param") PublishUserParam param);
    /**
     * 160068
     * 2018�???9�???10�??? 下午1:41:00
     * @param publishId
     * @param openId
     * @return
     */
    List<User> selectChildByPublishIdAndOpenId(@Param("publishId")String publishId,@Param("openId")String openId);
}