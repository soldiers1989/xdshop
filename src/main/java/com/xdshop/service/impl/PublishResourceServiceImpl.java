package com.xdshop.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.service.IPublishResourceService;

@Service
@Transactional(rollbackFor=Exception.class)
public class PublishResourceServiceImpl implements IPublishResourceService {
	private static final Logger logger = Logger.getLogger(PublishResourceServiceImpl.class);
	@Autowired
	private PublishResourceMapper publishResourceMapper;
	
	@Override
	public List<PublishResource> getPublishResourceList(PublishResource publishResource) throws Exception {
		return publishResourceMapper.selectPublishResourceList(publishResource);
	}

	@Override
	public String getOssKeyByPublishIdAndUrl(String publishId, String url) throws Exception {
		String ossKey = "";
		List<PublishResource> list = publishResourceMapper.selectByPublishIdAndUrl(publishId, url);
		if(list.size() > 0){
			PublishResource targetPublishResource = list.get(0);
			ossKey = targetPublishResource.getOssKey();
		}
		return ossKey;
	}
	
	

}
