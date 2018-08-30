package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.domain.Publish;
import com.xdshop.service.IPublishService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;

@Service
public class PublishServiceImpl implements IPublishService {
	private static final Logger logger = Logger.getLogger(PublishServiceImpl.class);
	@Autowired
	private PublishMapper publishMapper;
	@Override
	public Integer savePublish(Publish publish) throws Exception {
		//sql影响记录条数
		int recordNum = 0;
		if(publish.getId() == null){
			publish.setId(Utils.get16UUID());
			recordNum = publishMapper.insertSelective(publish);
		}else{
			recordNum = publishMapper.updateByPrimaryKeySelective(publish);
		}
		return recordNum;
	}
	@Override
	public List<Publish> getPublishList(BaseParam queryParam) throws Exception {
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		return publishMapper.selectPublishList(queryParam);
	}

}
