package com.xdshop.controller;


import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xdshop.service.IEventService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Utils;
import com.xdshop.util.XMLUtils;
import com.xdshop.vo.MsgRcvVo;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class EventController {
	private static final Logger logger = Logger.getLogger(EventController.class);
	
	@Autowired
	private XdShopService xdShopServiceImpl;
	@Autowired
	private IEventService eventServiceImpl;
	
	@ResponseBody
	@ApiOperation(value="交易")
	@RequestMapping(value="/zl",method=RequestMethod.GET)
	public String xdShop(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String signature,@RequestParam long timestamp ,@RequestParam String nonce,@RequestParam String echostr) throws Exception{
		
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(timestamp);
		String currTimeStr = Utils.formateDate2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss");
		logger.info("收到消息：");
		logger.info("signature:"+signature);
		logger.info("timestamp:"+timestamp+"，currTimeStr:"+currTimeStr);
		logger.info("nonce:"+nonce);
		logger.info("echostr:"+echostr);
		
		//验证签名
		if(xdShopServiceImpl.verifySinature(signature, timestamp, nonce)) {
			return echostr;
		}else{
			return "验证签名失败";
		}
	}
	
	@ResponseBody
	@ApiOperation(value="报文通信")
	@RequestMapping(value="/zl",method=RequestMethod.POST)
	public String xdShopPost(HttpServletRequest request,HttpServletResponse response,@RequestBody String recBody) throws Exception{
		logger.info("上送报文：\n"+recBody);
		MsgRcvVo msgRcv = XMLUtils.xmlToJaxBean(recBody, MsgRcvVo.class);
//		String fromUserName = msgRcv.getFromUserName();
//		String toUserName = msgRcv.getToUserName();
//		String content = msgRcv.getContent().toLowerCase();
		
		String msgRetStr = "";
		String eventName = msgRcv.getEvent();
		
		if(eventName != null){
			/**
			 * 接受到事件消息
			 */
			String event = eventName.toLowerCase();
			switch(event){
			case "subscribe":
				msgRetStr = eventServiceImpl.subscribe(msgRcv);
				break;
			case "unsubscribe":
				msgRetStr = eventServiceImpl.unsubscribe(msgRcv);
				break;
			case "scan":
				msgRetStr = eventServiceImpl.scan(msgRcv);
				break;
			}
		}else{
			/**
			 * 接受到其他消息
			 */
			msgRetStr = eventServiceImpl.genTextMsg(msgRcv);
		}
		return msgRetStr;
	}
	
	
}
