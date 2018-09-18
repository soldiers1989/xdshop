package com.xdshop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xdshop.dal.dao.AccessTokenMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.service.IAccessTokenService;


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
