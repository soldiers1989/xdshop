package com.xdshop.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.squareup.okhttp.Response;
import com.xdshop.api.PublishUserParam;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.UserMapper;
import com.xdshop.dal.dao.UserShareMapper;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.User;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.po.PublishPo;
import com.xdshop.service.IAccessTokenService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.IUserService;
import com.xdshop.util.HttpsUtil;
import com.xdshop.util.OkHttpUtil;
import com.xdshop.util.QrCodeUtil;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.ResponseVo;
import com.xdshop.vo.SceneVo;
import com.xdshop.vo.UserInfoVo;

@Service
@Transactional(rollbackFor=Exception.class)
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
		existUser.setNickName(URLEncoder.encode(userInfoVo.getNickname(), "UTF-8"));
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
	public List<User> getSubUser(String publishId,String openId) throws Exception {
		List<User> userList = userMapper.selectChildByPublishIdAndOpenId(publishId,openId);
		for (User user : userList) {
			user.setNickName(URLDecoder.decode(user.getNickName(), "UTF-8"));
		}
		return userList;
	}
	
	@Override
	public List<HashMap<String,Object>> getFetchUser(String publisId) throws Exception {
		List<HashMap<String,Object>> fetchUserList = userMapper.selectFetchUser(publisId);
		for (HashMap<String, Object> hashMap : fetchUserList) {
			hashMap.put("nickName", URLDecoder.decode(hashMap.get("nickName")+"", "UTF-8"));
		}
		return fetchUserList;
	}
	
	@Override
	public List<HashMap<String, Object>> getPublishUser(PublishUserParam queryParam) throws Exception {
		PageHelper.startPage(Integer.parseInt(queryParam.getCurPage()), Integer.parseInt(queryParam.getPageSize()),true);
		List<HashMap<String,Object>> list = userMapper.selectPublishUser(queryParam);
		/**
		 * nickName解码
		 */
		for (HashMap<String, Object> hashMap : list) {
			hashMap.put("nickName", URLDecoder.decode(hashMap.get("nickName")+"", "UTF-8"));
		}
		return list;
	}
	@Override
	public ResponseVo fetch(String publishId, String openId) throws Exception {
		ResponseVo rv = new ResponseVo();
		//查询门票领取状态
		UserShare userShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		if(userShare.getFetchStatus()) {
			throw new RuntimeException("门票领取失败！您已经成功领取门票，请勿重复领取！");
		}
		int updateCount = 0;
		/**
		 * 变更publish 门票数量
		 */
		Publish publish = publishServiceImpl.getPublish(publishId);
		//目前剩余票数
		int ticketRemainNow = publish.getTicketRemain();
		if(ticketRemainNow == 0) {
			rv.setSuccessResponse(false);
//			rv.setMessage("门票领取失败！当前活动门票已领完！");
			throw new RuntimeException("门票领取失败！当前活动门票已领完！");
		}
		//门票总数
		int ticketTotal = publish.getTicketTotal();
		//已领取
//		int ticketSale = publish.getTicketSale() + 1;
		//剩余
//		int ticketRemainNew = ticketTotal - ticketSale;
		int ticketRemainNew = ticketRemainNow - 1;

		publish.setTicketSale(ticketTotal - ticketRemainNew);
		publish.setTicketRemain(ticketRemainNew);
		/**
		 * 更新剩余票数
		 */
		PublishPo publishPo = new PublishPo();
		BeanUtils.copyProperties(publish, publishPo);
		publishPo.setTicketRemainNow(ticketRemainNow);
		updateCount = publishMapper.updateTicketNumber(publishPo);
		if(updateCount == 1) {
			rv.setSuccessResponse(true);
			rv.setMessage("门票领取成功！");
		}else {
			rv.setSuccessResponse(false);
			throw new RuntimeException("门票领取失败！当前编号门票已被其他顾客领取，请重新领取！");
		}
		
		/**
		 * 变更用户分享表门票领取状态
		 */
		userShare.setFetchStatus(true);
		userShareMapper.updateByPrimaryKeySelective(userShare);
		
		return rv;
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
	
	@Override
	public UserShare getUserShareInfo(String publishId,String openId) throws Exception {
		UserShare userShare = userShareMapper.selectByPublishIdAndOpenId(publishId, openId);
		return userShare;
	}
	
	
}
