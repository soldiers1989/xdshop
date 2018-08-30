package com.xdshop.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

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

import com.xdshop.service.XdShopService;
import com.xdshop.util.Utils;
import com.xdshop.util.XMLUtils;
import com.xdshop.vo.ArticleVo;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.MsgRetVo;

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
		logger.info("上送报文：\n"+recBody);
		MsgRcvVo msgRcv = XMLUtils.xmlToJaxBean(recBody, MsgRcvVo.class);
		String fromUserName = msgRcv.getFromUserName();
		String toUserName = msgRcv.getToUserName();
		String content = msgRcv.getContent();
		/**
		 * 自动回复消息：文本消息
		 */
		MsgRetVo  msgRet = new MsgRetVo();
		//回复文本消息
		/*msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setContent("欢迎关注本公众号!");
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgId(Utils.get16UUID());
		msgRet.setMsgType("text");*/
		
		//回复图文消息(最多支持回复8条图文消息)
		int articleCount = 8;
		msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgType("news");
		msgRet.setArticleCount(articleCount);	
		
		List<ArticleVo> articles = new ArrayList<ArticleVo>();
		for(int i = 1 ; i <= articleCount;i++){
			ArticleVo articleVo = new ArticleVo();
			articleVo.setTitle("标题：图文消息测试"+i);
//			articleVo.setDescription("描述：图文消息");
			articleVo.setPicUrl("http://thirdwx.qlogo.cn/mmopen/OxUBpiaYgpHgn4toKEsf4GopJbhicoIMia6JIOB757L4Iv9wg1WuUEKhBf6icVzibr9m765wV6CVULianzSQic8mxp0qw/132");
			articleVo.setUrl("http://baidu.com?openId=test");
			articles.add(articleVo);
		}
		msgRet.setArticles(articles);
		String msgRetStr = XMLUtils.jaxBeanToXml(msgRet);
		logger.info("返回报文：\n"+msgRetStr);
		return msgRetStr;
	}
	
	
}
