package com.xdshop.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;

public interface PublishResourceMapper {
    int deleteByPrimaryKey(String id);

	int insert(PublishResource record);

	int insertSelective(PublishResource record);

	PublishResource selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(PublishResource record);

	int updateByPrimaryKey(PublishResource record);
	
	List<PublishResource> selectPublishResourceList(@Param("queryParam")PublishResource queryParam);

}