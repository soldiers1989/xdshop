package com.xdshop.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.user.UserRegistryMessageHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.squareup.okhttp.Response;
import com.xdshop.api.BaseParam;
import com.xdshop.api.PublishUserParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.dao.UserMapper;
import com.xdshop.dal.dao.UserShareMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.dal.domain.User;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.schedule.AccessTokenScheduler;
import com.xdshop.service.IAccessTokenService;
import com.xdshop.service.IArticleService;
import com.xdshop.service.IEventService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.IUserService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.HttpsUtil;
import com.xdshop.util.OkHttpUtil;
import com.xdshop.util.QrCodeUtil;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.util.XMLUtils;
import com.xdshop.vo.ArticleVo;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.MsgRetVo;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.PublishVo;
import com.xdshop.vo.SceneVo;
import com.xdshop.vo.UserInfoVo;

import ch.qos.logback.core.subst.Token;
import xdshop.OssTest;

@Service
public class UserServiceImpl implements IUserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private IPublishService publishServiceImpl;
	@Autowired
	private IAccessTokenService accessTokenServiceImpl;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IOssService ossServiceImpl;
	@Autowired
	private PublishMapper publishMapper;
	@Autowired
	private UserShareMapper userShareMapper;
	
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss.bucketName}")
	private String bucketName;
	
	/**
	 * 获取头像字节数组
	 * @param userInfoVo
	 * @return
	 * @throws Exception
	 */
	public ByteArrayInputStream getHeaderImg(String  headerImgUrl) throws Exception{
		Map<String,String> params = new HashMap<String,String>();
		Response reponse = OkHttpUtil.get(headerImgUrl, params);
		byte [] headerImgByteArray = reponse.body().bytes();
		return new ByteArrayInputStream(headerImgByteArray);
	}
	/**
	 * 获取用户信息
	 * @param openId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public UserInfoVo getUserInfo(String openId,String accessToken) throws Exception{
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("access_token", accessToken);
		urlParams.put("openid", openId);
		String userInfoStr = HttpsUtil.doGet(baseUrl, urlParams);
		UserInfoVo userInfoVo = JSONObject.parseObject(userInfoStr, UserInfoVo.class);
		
		User existUser = userMapper.selectByOpenId(openId);
		existUser.setHeaderUrl(userInfoVo.getHeadimgurl());
		existUser.setNickName(userInfoVo.getNickname());
		userMapper.updateByPrimaryKeySelective(existUser);
		
		return userInfoVo;
	}
	@Override
	public InputStream getQrCode(String openId, String accessToken) throws Exception {
		InputStream isRet = null;
		
		//获取带参数二维码
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint(endpoint);
		ossVo.setAccessKeyId(accessKeyId);
		ossVo.setAccessKeySecret(accessKeySecret);
		ossVo.setBucketName(bucketName);
		
		User existUser = userMapper.selectByOpenId(openId);
		String ossKey = existUser.getQrCodeOssKey();
		if(ossKey == null){
			//生成带参数二维码并上传阿里云
			SceneVo sceneVo = new SceneVo();
			sceneVo.setOpenId(openId);
			InputStream qrPicIs = QrCodeUtil.getQrPic(sceneVo,accessToken);
			ossKey = "resource/"+openId+"/headerImg.jpg";
			ossServiceImpl.uploadFile(ossVo, ossKey, qrPicIs);
			existUser.setHeaderOssKey(ossKey);
			String qrCodeUrl = ossServiceImpl.getPermanentUrl(ossVo, ossKey);
			existUser.setQrCodeUrl(qrCodeUrl);
			existUser.setQrCodeOssKey(ossKey);
			
			userMapper.updateByPrimaryKeySelective(existUser);
			//获取阿里云文件（带参数二维码图片）
			isRet  = ossServiceImpl.getObject(ossVo, ossKey);
		}else{
			isRet = ossServiceImpl.getObject(ossVo, ossKey);
		}
		return isRet;
	}
	@Override
	public List<User> getSubUser(String openId) throws Exception {
		return userMapper.selectByParentOpenId(openId);
	}
	
	@Override
	public List<HashMap<String,Object>> getFetchUser(String publisId) throws Exception {
		return userMapper.selectFetchUser(publisId);
	}
	
	@Override
	public List<HashMap<String, Object>> getPublishUser(PublishUserParam queryParam) throws Exception {
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		return userMapper.selectPublishUser(queryParam);
	}
	@Override
	public int fetch(String publishId, String openId) throws Exception {
		int updateCount = 0;
		/**
		 * 变更publish 门票数量
		 */
		Publish publish = publishServiceImpl.getPublish(publishId);
		//门票总数
		int ticketTotal = publish.getTicketTotal();
		//已领取
		int ticketSale = publish.getTicketSale() + 1;
		//剩余
		int ticketRemain = ticketTotal - ticketSale;

		publish.setTicketSale(ticketSale);
		publish.setTicketRemain(ticketRemain);
		publishMapper.updateByPrimaryKeySelective(publish);
		
		/**
		 * 变更用户分享表门票领取状态
		 */
		UserShare userShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		userShare.setFetchStatus(true);
		updateCount = userShareMapper.updateByPrimaryKeySelective(userShare);
		return updateCount;
	}
	@Override
	public int unfetch(String publishId, String openId) throws Exception {
		int updateCount = 0;
		/**
		 * 变更publish 门票数量
		 */
		Publish publish = publishServiceImpl.getPublish(publishId);
		//门票总数
		int ticketTotal = publish.getTicketTotal();
		//已领取
		int ticketSale = publish.getTicketSale() - 1;
		//剩余
		int ticketRemain = ticketTotal - ticketSale;

		publish.setTicketSale(ticketSale);
		publish.setTicketRemain(ticketRemain);
		publishMapper.updateByPrimaryKeySelective(publish);
		
		/**
		 * 变更用户分享表门票领取状态
		 */
		UserShare userShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		userShare.setFetchStatus(false);
		updateCount = userShareMapper.updateByPrimaryKeySelective(userShare);
		return updateCount;
	}
	@Override
	public String getPosterOssUrl(String publishId, String openId) throws Exception {
		String posterOssUrl = "";
		UserShare userShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		if(userShare != null)
			posterOssUrl = userShare.getPosterOssUrl();
		return posterOssUrl;
	}
	@Override
	public User getUserInfo(String openId) throws Exception {
		return userMapper.selectByOpenId(openId);
	}
	
	
}
