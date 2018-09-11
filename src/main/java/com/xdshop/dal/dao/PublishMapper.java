package com.xdshop.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;
import com.xdshop.po.PublishPo;

public interface PublishMapper {
    int deleteByPrimaryKey(String id);

	int insert(Publish record);

	int insertSelective(Publish record);

	Publish selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Publish record);

	int updateByPrimaryKey(Publish record);

	

	
	Publish selectCurrPublish();
	
	List<Publish> selectAllPublish();

	

	

	
	List<Publish> selectPublishList(@Param("queryParam")BaseParam queryParam);
	
	int updateTicketNumber(PublishPo record);
	

}