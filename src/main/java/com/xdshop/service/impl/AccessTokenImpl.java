package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.dao.AccessTokenMapper;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.service.IAccessTokenService;
import com.xdshop.service.IArticleService;
import com.xdshop.service.IEventService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.util.XMLUtils;
import com.xdshop.vo.ArticleVo;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.MsgRetVo;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.PublishVo;

import xdshop.OssTest;

@Service
public class AccessTokenImpl implements IAccessTokenService {
	private static final Logger logger = Logger.getLogger(AccessTokenImpl.class);
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	@Override
	public AccessToken getAccessToken() {
		return accessTokenMapper.selectByPrimaryKey("1");
	}
	
}
