package com.xdshop.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xdshop.dal.dao.UserShareMapper;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.service.IUserShareService;
import com.xdshop.util.Utils;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserShareServiceImpl implements IUserShareService {
	private static final Logger logger = Logger.getLogger(UserShareServiceImpl.class);
	@Autowired
	private UserShareMapper userShareMapper;
	@Override
	public UserShare saveUserShare(UserShare userShare) throws Exception {
		String publishId = userShare.getPublishId();
		String openId = userShare.getOpenId();
		UserShare existUserShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		if(existUserShare == null){
			userShare.setId(Utils.get16UUID());
			userShare.setFetchStatus(false);
			userShareMapper.insertSelective(userShare);
		}else{
			existUserShare.setPosterOssKey(userShare.getPosterOssKey());
			existUserShare.setPosterOssUrl(userShare.getPosterOssUrl());
			userShareMapper.updateByPrimaryKeySelective(existUserShare);
			userShare = existUserShare;
		}
		return userShare;
	}
	
	
}
