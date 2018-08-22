package com.pujjr.gps.controller;


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

import com.pujjr.gps.service.XdShopService;
import com.pujjr.gps.util.Utils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class XdShopController {
	private static final Logger logger = Logger.getLogger(XdShopController.class);
	
	@Autowired
	private XdShopService xdShopServiceImpl;
	
	@ResponseBody
	@ApiOperation(value="交易")
	@RequestMapping(value="/xdshop",method=RequestMethod.GET)
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
	@RequestMapping(value="/xdshop",method=RequestMethod.POST)
	public String xdShopPost(HttpServletRequest request,HttpServletResponse response,@RequestBody String recBody) throws Exception{
		logger.info("接收报文："+recBody);
		return "";
	}
	
	
}
