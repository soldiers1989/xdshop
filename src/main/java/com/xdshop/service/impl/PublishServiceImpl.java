package com.xdshop.service.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.internal.OSSUtils;
import com.github.pagehelper.PageHelper;
import com.netflix.discovery.converters.Auto;
import com.netflix.servo.monitor.PublishingPolicy;
import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.dao.AccessTokenMapper;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.dao.UserMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.dal.domain.User;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.service.IArticleService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishResourceService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.IUserService;
import com.xdshop.service.IUserShareService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.ImageUtils;
import com.xdshop.util.QrCodeUtil;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.PublishVo;
import com.xdshop.vo.SceneVo;
import com.xdshop.vo.UserInfoVo;

import xdshop.ImageTest;
import xdshop.ImageTest2;
import xdshop.OssTest;
import xdshop.QrCodeTest;
import xdshop.UserInfoTest;

@Service
public class PublishServiceImpl implements IPublishService {
	private static final Logger logger = Logger.getLogger(PublishServiceImpl.class);
	@Autowired
	private PublishMapper publishMapper;
	@Autowired
	private PublishResourceMapper publishResourceMapper;
	@Autowired
	private IOssService ossServiceImpl;
	@Autowired
	private IArticleService articleServiceImpl;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	@Autowired
	private IPublishResourceService publishResourceServiceImpl;
	@Autowired
	private IUserShareService userShareServiceImpl;
	@Autowired
	private IUserService userServiceImpl;
	
	@Value("${oss.endpoint}")
	private String endpoint;
	@Value("${oss.accessKeyId}")
	private String accessKeyId;
	@Value("${oss.accessKeySecret}")
	private String accessKeySecret;
	@Value("${oss.bucketName}")
	private String bucketName;
	
	
	@Override
	public Publish initPublish() throws Exception {
		Publish publish = new Publish();
		publish.setId(Utils.get16UUID());
		publish.setOpenFlag(false);
		publish.setCreateTime(Calendar.getInstance().getTime());
		publishMapper.insertSelective(publish);
		return publish;
	}

	
	@Override
	public Integer savePublish(PublishVo publish) throws Exception {
		//sql影响记录条数
		int recordNum = 0;
		//保存图文消息
		Article article = publish.getArticle();
		if(article != null){
			articleServiceImpl.saveArticle(article);
		}
				
		//获取海报OSSkey
		String posterUrl = publish.getPosterUrl();
		String publishId = publish.getId();
		String posterOssKey = publishResourceServiceImpl.getOssKeyByPublishIdAndUrl(publishId, posterUrl);
		publish.setPosterOssKey(posterOssKey);
		publish.setTicketRemain(publish.getTicketTotal());
		publish.setTicketSale(0);
		//保存发布信息
		recordNum = publishMapper.updateByPrimaryKeySelective(publish);
		
		return recordNum;
	}
	
	@Override
	public Integer openPublish(PublishVo publish) throws Exception {
		//sql影响记录条数
		int recordNum = 0;
		boolean openFlag = publish.getOpenFlag();
		String id = publish.getId();
		//如果启用当前发布，则停止其余所有发布
		if(openFlag){
			List<Publish> allPublishList = publishMapper.selectAllPublish();
			for (Publish tempPublish : allPublishList) {
				if(!id.equals(tempPublish.getId())){
					tempPublish.setOpenFlag(false);
					publishMapper.updateByPrimaryKeySelective(tempPublish);
				}
			}
		}
		recordNum = publishMapper.updateByPrimaryKeySelective(publish);
		return recordNum;
	}
	
	@Override
	public List<Publish> getPublishList(BaseParam queryParam) throws Exception {
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		return publishMapper.selectPublishList(queryParam);
	}
	@Override
	public void uploadResource(MultipartFile file, String typeCode, String publishId) throws Exception {
		String fileName = file.getOriginalFilename();
		logger.info("当前文件："+fileName);

		/*String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		String accessKeyId = "LTAINChIcoUp3q8t";
		String accessKeySecret = "MxTKnqTw2MvegsEmNUrY3ovdWTtFjy";
		String bucketName = "xdshop2018";*/
		
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint(endpoint);
		ossVo.setAccessKeyId(accessKeyId);
		ossVo.setAccessKeySecret(accessKeySecret);
		ossVo.setBucketName(bucketName);
		
		String ossKey = "resource/"+ publishId +"/"+fileName;
		
		ossServiceImpl.uploadFile(ossVo, ossKey, file.getInputStream());
//		String url = ossServiceImpl.getUrl(ossVo, ossKey);
		String url = ossServiceImpl.getPermanentUrl(ossVo, ossKey);
		
		PublishResource publishResource = new PublishResource();
		publishResource.setId(Utils.get16UUID());
		publishResource.setOssKey(ossKey);
		publishResource.setUrl(url);
		publishResource.setPublishId(publishId);
		publishResource.setTypeCode(typeCode);
		publishResource.setTypeName("资源名称");
		publishResourceMapper.insert(publishResource);
	}


	@Override
	public Publish getPublish(String id) throws Exception {
		return publishMapper.selectByPrimaryKey(id);
	}


	@Override
	public String generalSharePic(ShareParamVo shareParamVo) throws Exception {
		String openId = shareParamVo.getOpenId();
		String publishId = shareParamVo.getPublishId();
		/**
		 * 保存用户信息
		 */
		User user = userMapper.selectByOpenId(shareParamVo.getOpenId());
		if(user == null){
			throw new RuntimeCryptoException("用户openId:"+shareParamVo.getOpenId()+"不存在(未关注公众号)");
		}
		user.setMobile(shareParamVo.getMobile());
		user.setName(shareParamVo.getName());
		userMapper.updateByPrimaryKeySelective(user);
		
		/**
		 * 获取发布信息
		 */
		Publish publish = publishMapper.selectByPrimaryKey(shareParamVo.getPublishId());
		
		/**
		 * 获取accessToken
		 */
		AccessToken accessToken = accessTokenMapper.selectByPrimaryKey("1");
		
		//获取oss参数
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint(endpoint);
		ossVo.setAccessKeyId(accessKeyId);
		ossVo.setAccessKeySecret(accessKeySecret);
		ossVo.setBucketName(bucketName);
		
		//获取底图
		InputStream posterInputStream = ossServiceImpl.getObject(ossVo, publish.getPosterOssKey());
		BufferedImage bgImage = ImageIO.read(posterInputStream);
		
		//背景宽
		int bgWith = bgImage.getWidth();
		//背景高
		int bgHeight = bgImage.getHeight();
		System.out.println(bgWith+"|"+bgHeight);
		Graphics2D g2d = bgImage.createGraphics();
		
		//获取场景二维码方法一：获取公众号场景二维码（附带分享人openId）
		SceneVo sceneVo = new SceneVo();
		sceneVo.setOpenId(openId);
		BufferedImage qrImage = ImageIO.read(QrCodeUtil.getQrPic(sceneVo,accessToken.getAccessToken()));
		//获取场景二维码方法二：采用阿里云模式存储带场景二维码
		/*InputStream qrInputStrem = userServiceImpl.getQrCode(openId, accessToken.getAccessToken());
		BufferedImage qrImage = ImageIO.read(qrInputStrem);*/
//		System.out.println(bgImage.getHeight()+"|"+bgImage.getWidth());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f));
		int qrImageWidth = qrImage.getWidth()/2 + 50;
		int qrImageHeight = qrImage.getHeight()/2 + 50;
//		System.out.println("bgWith|qrImageWidth:"+bgWith+"|"+qrImageWidth);
		g2d.drawImage(qrImage,  bgWith - qrImageWidth, bgHeight - qrImageHeight-60,qrImageWidth ,qrImageHeight ,null);
			
		//获取用户数据：头像
		String headerUrl = "";
		if(user.getHeaderUrl() != null){
			headerUrl = user.getHeaderUrl();
		}else{
			headerUrl = userServiceImpl.getUserInfo(openId, accessToken.getAccessToken()).getHeadimgurl();
		}
		BufferedImage headerImage = ImageIO.read(userServiceImpl.getHeaderImg(headerUrl));
		int headerImgWith = headerImage.getWidth()/2;
		int headerImgHeight = headerImage.getHeight()/2;
		System.out.println("头像宽|高:"+headerImgWith+"|"+headerImgHeight);
		g2d.drawImage(headerImage, 15, bgHeight - qrImageHeight - 120, headerImgWith, headerImgHeight, null);
		
		//写入：别名
		String nickName = "";
		if(user.getNickName() != null && !user.getNickName().equals("")){
			nickName = user.getNickName();
		}else{
			nickName = userServiceImpl.getUserInfo(openId, accessToken.getAccessToken()).getNickname();
		}
		BufferedImage nickNameImage = ImageUtils.createContentImage(nickName, 400, 40, 20,Color.black);
		int nickNameImageWidth =nickNameImage.getWidth();
		int nickNameHeight =nickNameImage.getHeight();
		g2d.drawImage(nickNameImage,headerImgWith +30 , bgHeight - qrImageHeight - 100, nickNameImageWidth, nickNameHeight,null);
			
		//写入：长按二维码进入领取
		String pressTips = "长按二维码进入领取";
		BufferedImage pressImage = ImageUtils.createContentImage(pressTips, 400, 40, 20,Color.black);
		int pressImageWidth =pressImage.getWidth();
		int pressImageHeight =pressImage.getHeight();
		g2d.drawImage(pressImage,bgWith - qrImageWidth + 20 , bgHeight - qrImageHeight - 100, pressImageWidth, pressImageHeight,null);

		String operTips = "把海报分享到朋友圈和微信群可以更快获得免费门票哦";
		BufferedImage operTipsImage = ImageUtils.createContentImage(operTips, 600, 100, 40,Color.red);
		int operTipsImageWidth =operTipsImage.getWidth();
		int operTipsImageHeight =operTipsImage.getHeight();
		g2d.drawImage(operTipsImage,10 , bgHeight - qrImageHeight - 50, operTipsImageWidth, operTipsImageHeight,null);
		
		String joinMe = "快来和我一起领取吧 ￥0";
		BufferedImage joinMeImage = ImageUtils.createContentImage(joinMe, 600, 60, 40,Color.red);
		int joinMeImageWidth =joinMeImage.getWidth();
		int joinMeImageHeight =joinMeImage.getHeight();
		g2d.drawImage(joinMeImage,10, bgHeight - qrImageHeight + 100, joinMeImageWidth, joinMeImageHeight,null);
		
		//上传分享海报
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bgImage,"jpg", baos);
		byte[] posterByteArray = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(posterByteArray);
		String sharePosterOssKey = "resource/"+openId+"/"+publishId+"/"+"分享海报.jpg";
		ossServiceImpl.uploadFile(ossVo, sharePosterOssKey, bais);
		
		//获取分享海报URL
		String sharePicUrl = ossServiceImpl.getPermanentUrl(ossVo, sharePosterOssKey);
		//更新用户分享表
		UserShare userShare = new UserShare();
		userShare.setOpenId(openId);
		userShare.setPublishId(publishId);
		userShare.setPosterOssKey(sharePosterOssKey);
		userShare.setPosterOssUrl(sharePicUrl);
		userShare.setFetchStatus(false);
		userShareServiceImpl.saveUserShare(userShare);
		
		return sharePicUrl;
	}


	@Override
	public Publish getCurrPublish() throws Exception {
		return publishMapper.selectCurrPublish();
	}


	@Override
	public Publish generalFistSharePic(String publishId) throws Exception {
		//系统虚拟openId
		String openId = "vopenid01";
		
		/**
		 * 获取发布信息
		 */
		Publish publish = publishMapper.selectByPrimaryKey(publishId);
		
		if(!(publish.getPushPosterUrl() == null || "".equals(publish.getPushPosterUrl()))){
			return publish;
		}
		
		/**
		 * 获取accessToken
		 */
		AccessToken accessToken = accessTokenMapper.selectByPrimaryKey("1");
		
		//获取oss参数
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint(endpoint);
		ossVo.setAccessKeyId(accessKeyId);
		ossVo.setAccessKeySecret(accessKeySecret);
		ossVo.setBucketName(bucketName);
		
		//获取底图
		InputStream posterInputStream = ossServiceImpl.getObject(ossVo, publish.getPosterOssKey());
		BufferedImage bgImage = ImageIO.read(posterInputStream);
		
		//背景宽
		int bgWith = bgImage.getWidth();
		//背景高
		int bgHeight = bgImage.getHeight();
		System.out.println(bgWith+"|"+bgHeight);
		Graphics2D g2d = bgImage.createGraphics();
		
		//获取场景二维码方法一：获取公众号场景二维码（附带分享人openId）
		SceneVo sceneVo = new SceneVo();
		sceneVo.setOpenId(openId);
		BufferedImage qrImage = ImageIO.read(QrCodeUtil.getQrPic(sceneVo,accessToken.getAccessToken()));
		//获取场景二维码方法二：采用阿里云模式存储带场景二维码
		/*InputStream qrInputStrem = userServiceImpl.getQrCode(openId, accessToken.getAccessToken());
		BufferedImage qrImage = ImageIO.read(qrInputStrem);*/
//		System.out.println(bgImage.getHeight()+"|"+bgImage.getWidth());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f));
		int qrImageWidth = qrImage.getWidth()/2 + 50;
		int qrImageHeight = qrImage.getHeight()/2 + 50;
//		System.out.println("bgWith|qrImageWidth:"+bgWith+"|"+qrImageWidth);
		g2d.drawImage(qrImage,  bgWith - qrImageWidth, bgHeight - qrImageHeight-60,qrImageWidth ,qrImageHeight ,null);
			
		//公众号名称
		String ghName = "重庆旅游生活宝";
		BufferedImage nickNameImage = ImageUtils.createContentImage(ghName, 400, 40, 30,Color.black);
		int nickNameImageWidth =nickNameImage.getWidth();
		int nickNameHeight =nickNameImage.getHeight();
		g2d.drawImage(nickNameImage,20 , bgHeight - qrImageHeight - 100, nickNameImageWidth, nickNameHeight,null);
		
		//写入：长按二维码进入领取
		String pressTips = "长按二维码进入领取";
		BufferedImage pressImage = ImageUtils.createContentImage(pressTips, 400, 40, 20,Color.black);
		int pressImageWidth =pressImage.getWidth();
		int pressImageHeight =pressImage.getHeight();
		g2d.drawImage(pressImage,bgWith - qrImageWidth + 20 , bgHeight - qrImageHeight - 100, pressImageWidth, pressImageHeight,null);

		String operTips = "把海报分享到朋友圈和微信群可以更快获得免费门票哦";
		BufferedImage operTipsImage = ImageUtils.createContentImage(operTips, 600, 100, 40,Color.red);
		int operTipsImageWidth =operTipsImage.getWidth();
		int operTipsImageHeight =operTipsImage.getHeight();
		g2d.drawImage(operTipsImage,20 , bgHeight - qrImageHeight - 50, operTipsImageWidth, operTipsImageHeight,null);
		
		String joinMe = "快来和我一起领取吧￥0";
		BufferedImage joinMeImage = ImageUtils.createContentImage(joinMe, 600, 60, 40,Color.red);
		int joinMeImageWidth =joinMeImage.getWidth();
		int joinMeImageHeight =joinMeImage.getHeight();
		g2d.drawImage(joinMeImage,20 , bgHeight - qrImageHeight + 100, joinMeImageWidth, joinMeImageHeight,null);
		
		//上传分享海报
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bgImage,"jpg", baos);
		byte[] posterByteArray = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(posterByteArray);
		String sharePosterOssKey = "resource/"+openId+"/"+publishId+"/"+"消息推送分享海报.jpg";
		ossServiceImpl.uploadFile(ossVo, sharePosterOssKey, bais);
		
		//获取分享海报URL
		String sharePicUrl = ossServiceImpl.getPermanentUrl(ossVo, sharePosterOssKey);
		
		Publish publishRet = publishMapper.selectByPrimaryKey(publishId);
		publishRet.setPushPosterOssKey(sharePosterOssKey);
		publishRet.setPushPosterUrl(sharePicUrl);
		publishMapper.updateByPrimaryKeySelective(publishRet);
		return publishRet;
	}


}
