package com.xdshop.service;

import com.xdshop.vo.MsgRcvVo;

public interface IEventService {
	
	public String subscribe(MsgRcvVo msgRcv) throws Exception;
	public String unsubscribe(MsgRcvVo msgRcv) throws Exception;
	public String scan(MsgRcvVo msgRcv) throws Exception;
	//生成文本消息
	public String genTextMsg(MsgRcvVo msgRcv) throws Exception;
	
}
