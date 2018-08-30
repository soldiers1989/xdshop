package com.xdshop.service;

import java.util.List;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;

public interface IPublishService {
	public Integer savePublish(Publish publish) throws Exception;
	
	public List<Publish> getPublishList(BaseParam baseParam) throws Exception;
	
}
