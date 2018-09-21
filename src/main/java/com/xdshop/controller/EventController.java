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
		
		String event = msgRcv.getEvent().toLowerCase();
		String msgRetStr = "";
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
		
		/**
		 * 自动回复消息：文本消息
		 */
//		MsgRetVo  msgRet = new MsgRetVo();
		//回复文本消息
		/*msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setContent("欢迎关注本公众号!");
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgId(Utils.get16UUID());
		msgRet.setMsgType("text");*/
		
		//回复图文消息(最多支持回复8条图文消息)
		/*int articleCount = 1;
		msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgType("news");
		msgRet.setArticleCount(articleCount);	
		
		List<ArticleVo> articles = new ArrayList<ArticleVo>();
		for(int i = 1 ; i <= articleCount;i++){
			ArticleVo articleVo = new ArticleVo();
			articleVo.setTitle("【爱在曼谷园】水陆联欢，日夜通玩，快带心爱的TA来免费畅玩吧！");
//			articleVo.setDescription("描述：图文消息");
			articleVo.setPicUrl("http://xdshop2018.oss-cn-hangzhou.aliyuncs.com/resource/微信图片_20180824114749.jpg");
			articleVo.setUrl("http://zl.bnxly.top/app/xdshop_c/index_publish.html?_ijt=ojcbtgbgkgtu5q1ln6mstttpnv#/publishshow/oXmQ_1ddd8Yq4C_oAhq_OiMG181c/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiYWRtaW4iLCJpYXQiOjE1MzU4MTcxNTF9.hmszfiLDY8MZKbjYtJ_clhYlVRp75Ovt0q48wQGpsXI/a2ed849d18722273");
			articles.add(articleVo);
		}
		msgRet.setArticles(articles);
		String msgRetStr = XMLUtils.jaxBeanToXml(msgRet);*/
//		logger.info("返回报文：\n"+msgRetStr);
		return msgRetStr;
	}
	
	
}
