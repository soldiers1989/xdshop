package com.pujjr.gps.controller;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pujjr.gps.util.Utils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class XdShopController {
	private static final Logger logger = Logger.getLogger(XdShopController.class);
	@ResponseBody
	@ApiOperation(value="交易")
	@RequestMapping(value="/xdShop",method=RequestMethod.GET)
	public String xdShop(HttpServletRequest request,HttpServletResponse response,
			@RequestParam String signature,@RequestParam long timestamp ,@RequestParam String nonce,@RequestParam String echostr){
		logger.info("收到消息：");
		
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(timestamp);
		String currTimeStr = Utils.formateDate2String(cl.getTime(), "yyyy-MM-dd HH:mm:ss");
		
		Map<String,String> dcryptMap = new HashMap<String,String>();
		logger.info("signature:"+signature);
		logger.info("timestamp:"+timestamp+"，currTimeStr:"+currTimeStr);
		logger.info("nonce:"+nonce);
		logger.info("echostr:"+echostr);
		
		return "交易返回";
	}
	
	
}
