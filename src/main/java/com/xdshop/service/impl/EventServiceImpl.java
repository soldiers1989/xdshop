package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.dao.UserMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.dal.domain.User;
import com.xdshop.schedule.AccessTokenScheduler;
import com.xdshop.service.IAccessTokenService;
import com.xdshop.service.IArticleService;
import com.xdshop.service.IEventService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.IUserService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.util.XMLUtils;
import com.xdshop.vo.ArticleVo;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.MsgRetVo;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.PublishVo;
import com.xdshop.vo.UserInfoVo;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import xdshop.OssTest;

@Service
public class EventServiceImpl implements IEventService {
	private static final Logger logger = Logger.getLogger(EventServiceImpl.class);
	@Autowired
	private IPublishService publishServiceImpl;
	@Autowired
	private IAccessTokenService accessTokenServiceImpl;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private IArticleService articleServiceImpl;
	
	@Value("${article.picBaseUrl}")
	private String articlePicBaseUrl;
	@Override
	public String subscribe(MsgRcvVo msgRcv) throws Exception {
		String fromUserName = msgRcv.getFromUserName();
		String toUserName = msgRcv.getToUserName();
//		String content = msgRcv.getContent().toLowerCase();
		
		//获取token
		AccessToken accessToken = accessTokenServiceImpl.getAccessToken();
		//用户openId
		String openId = fromUserName;
		//公众号ID
		String ghId = toUserName;
		//场景值（推荐人openId）
		String qrScene = msgRcv.getEventKey();
		String parentOpenId = qrScene.substring(8, qrScene.length());
		//推荐人信息
		User parentUser = userMapper.selectByOpenId(parentOpenId);
		//关注状态，关注：true   取消关注：false
		boolean subType = true;
		
		//保存客户信息
		User existUser = userMapper.selectByOpenId(openId);
		if(existUser == null){
			
			User user = new User();
			user.setId(Utils.get16UUID());
			user.setOpenId(openId);
//			user.setNickName(userInfoVo.getNickname());
//			user.setHeaderUrl(userInfoVo.getHeadimgurl());
			user.setHeaderOssKey("");
			user.setParentOpenId(parentOpenId);
			user.setSubType(subType);
			user.setCreateTime(Calendar.getInstance().getTime());
			userMapper.insertSelective(user);
			
			//获取用户信息(微信头像，昵称等)
			UserInfoVo userInfoVo = userServiceImpl.getUserInfo(openId, accessToken.getAccessToken());
			
		}else{
			//如果之前客户关注过，但是取消了关注后，再次重新关注，直接将关注状态字段变更为：true;
			existUser.setSubType(true);
			userMapper.updateByPrimaryKeySelective(existUser);
			
			//获取用户信息(微信头像，昵称等)
			UserInfoVo userInfoVo = userServiceImpl.getUserInfo(openId, accessToken.getAccessToken());
		}
		
		Publish currPublish = publishServiceImpl.getCurrPublish();
		
		MsgRetVo msgRet = new MsgRetVo();
		int articleCount = 1;
		msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgType("news");
		msgRet.setArticleCount(articleCount);	
		List<ArticleVo> articles = new ArrayList<ArticleVo>();
		for(int i = 1 ; i <= articleCount;i++){
			ArticleVo articleVo = new ArticleVo();
			articleVo.setTitle(currPublish.getTitle());
			if("vopenid01".equals(parentOpenId)){
				articleVo.setDescription("您已成功助力好友："+parentUser.getNickName()+",点击邀请好友助力，获取免费门票!");
			}else{
				articleVo.setDescription("点击邀请好友助力，获取免费门票!");
			}
			
			//获取当前活动，图文消息，图片地址
			String publishId = currPublish.getId();
			Article currPublishArticle = articleServiceImpl.getArticle(publishId);
			articleVo.setPicUrl(currPublishArticle.getPicUrl());
			//设置：点击图文消息，跳转地址。例子：http://zl.bnxly.top/app/xdshop_c/index_publish.html?#/publishshow/oXmQ_1ddd8Yq4C_oAhq_OiMG181c/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiYWRtaW4iLCJpYXQiOjE1MzU4MTcxNTF9.hmszfiLDY8MZKbjYtJ_clhYlVRp75Ovt0q48wQGpsXI/a2ed849d18722273
			String Authorization = Jwts.builder().setSubject(openId)
		            .claim("roles", openId).setIssuedAt(new Date())
		            .signWith(SignatureAlgorithm.HS256, "xdshop_pkey").compact();
			String picUrl = articlePicBaseUrl+"/"+openId+"/"+Authorization+"/"+publishId;
			logger.info("图文消息跳转地址："+picUrl);
			articleVo.setUrl(picUrl);
			articles.add(articleVo);
		}
		msgRet.setArticles(articles);
		String msgRetStr = XMLUtils.jaxBeanToXml(msgRet);
		
		logger.info("返回报文：\n"+msgRetStr);
		return msgRetStr;
	}

	@Override
	public String unsubscribe(MsgRcvVo msgRcv) throws Exception {
		String fromUserName = msgRcv.getFromUserName();
		String toUserName = msgRcv.getToUserName();
	
		//用户openId
		String openId = fromUserName;
		
		//保存客户信息
		User user = userMapper.selectByOpenId(openId);
		user.setSubType(false);
		userMapper.updateByPrimaryKeySelective(user);

		String msgRetStr = "";
		logger.info("返回报文：\n"+msgRetStr);
		return msgRetStr;
	}

	@Override
	public String scan(MsgRcvVo msgRcv) throws Exception {
		String fromUserName = msgRcv.getFromUserName();
		String toUserName = msgRcv.getToUserName();
//		String content = msgRcv.getContent().toLowerCase();
		
		//获取token
		AccessToken accessToken = accessTokenServiceImpl.getAccessToken();
		//用户openId
		String openId = fromUserName;
		//公众号ID
		String ghId = toUserName;
		//场景值（推荐人openId）
		String qrScene = msgRcv.getEventKey();
		String parentOpenId = qrScene;
		//推荐人信息
		User parentUser = userMapper.selectByOpenId(parentOpenId);
		
		//当前发布
		Publish currPublish = publishServiceImpl.getCurrPublish();
		
		MsgRetVo msgRet = new MsgRetVo();
		int articleCount = 1;
		msgRet.setFromUserName(toUserName);
		msgRet.setToUserName(fromUserName);
		msgRet.setCreateTime(System.currentTimeMillis());
		msgRet.setMsgType("news");
		msgRet.setArticleCount(articleCount);	
		List<ArticleVo> articles = new ArrayList<ArticleVo>();
		for(int i = 1 ; i <= articleCount;i++){
			ArticleVo articleVo = new ArticleVo();
			articleVo.setTitle(currPublish.getTitle());
			articleVo.setDescription("点击邀请好友助力，获取免费门票!");
			//获取当前活动，图文消息，图片地址
			String publishId = currPublish.getId();
			Article currPublishArticle = articleServiceImpl.getArticle(publishId);
			articleVo.setPicUrl(currPublishArticle.getPicUrl());
			//设置：点击图文消息，跳转地址。例子：http://zl.bnxly.top/app/xdshop_c/index_publish.html?#/publishshow/oXmQ_1ddd8Yq4C_oAhq_OiMG181c/eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiYWRtaW4iLCJpYXQiOjE1MzU4MTcxNTF9.hmszfiLDY8MZKbjYtJ_clhYlVRp75Ovt0q48wQGpsXI/a2ed849d18722273
			String Authorization = Jwts.builder().setSubject(openId)
		            .claim("roles", openId).setIssuedAt(new Date())
		            .signWith(SignatureAlgorithm.HS256, "xdshop_pkey").compact();
			String picUrl = articlePicBaseUrl+"/"+openId+"/"+Authorization+"/"+publishId;
			logger.info("图文消息跳转地址："+picUrl);
			articleVo.setUrl(picUrl);
			articles.add(articleVo);
		}
		msgRet.setArticles(articles);
		String msgRetStr = XMLUtils.jaxBeanToXml(msgRet);
		
		logger.info("返回报文：\n"+msgRetStr);
		return msgRetStr;
	}
	
}
