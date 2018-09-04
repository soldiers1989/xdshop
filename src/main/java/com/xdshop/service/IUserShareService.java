package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.PublishVo;

public interface IUserShareService {
	public UserShare saveUserShare(UserShare userShare) throws Exception;
	
	
}
