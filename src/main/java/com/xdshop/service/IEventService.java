package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.domain.Publish;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.PublishVo;

public interface IEventService {
	
	public String subscribe(MsgRcvVo msgRcv) throws Exception;
	public String unsubscribe(MsgRcvVo msgRcv) throws Exception;
	public String scan(MsgRcvVo msgRcv) throws Exception;
	
}
