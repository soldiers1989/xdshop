package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishResourceService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.vo.OssBaseVo;

import xdshop.OssTest;

@Service
public class PublishResourceServiceImpl implements IPublishResourceService {
	private static final Logger logger = Logger.getLogger(PublishResourceServiceImpl.class);
	@Autowired
	private PublishResourceMapper publishResourceMapper;
	
	@Override
	public List<PublishResource> getPublishResourceList(PublishResource publishResource) throws Exception {
		return publishResourceMapper.selectPublishResourceList(publishResource);
	}
	
	

}
