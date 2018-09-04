package com.xdshop.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xdshop.dal.dao.AccessTokenMapper;
import com.xdshop.dal.domain.AccessToken;
import com.xdshop.service.base.BaseService;
import com.xdshop.util.HttpsUtil;
import com.xdshop.util.Utils;

@Service
@EnableScheduling
public class AccessTokenScheduler extends BaseService<AccessTokenScheduler> {
	@Autowired
	private AccessTokenMapper accessTokenMapper;
//	private static final int REFRESH_DURE = 90;
	@Value("${weChat.appId}")
	private String weChatAppId;
	@Value("${weChat.secret}")
	private String weChatSecret;
	@Value("${weChat.token.refreshFreq}")
	private int refreshFreq;
	
	@Scheduled(cron = "${schedule.tokenRefresh.cronTime}") 
	public void autoUploadAllDataToSeg() {
		/**
		 * 星都优选
		 * String appId = "wxcc4c443d5a08ed7d";
			String secret = "89ece1ca08163acc1737155060b33eb1";
			测试账号：
			wxe446196f5726d322
			9be409fa349f1cd84e42dcddcff64703
		 */	
		AccessToken accessToken = accessTokenMapper.selectByPrimaryKey("1");
		//当前access_token刷新时间
		Date refreshTime = accessToken.getRefreshTime();
		//计算下次access_token刷新时间
		Calendar nextRefreshCl = Calendar.getInstance();
		nextRefreshCl.setTime(refreshTime);
		nextRefreshCl.add(Calendar.MINUTE,refreshFreq);
		Date nextRefreshTime = nextRefreshCl.getTime();
		Date dateNow = Calendar.getInstance().getTime();
		
		//到达下一次刷新时间
		if(nextRefreshTime.before(dateNow)) {
			logger.info("刷新AccessToken");
			String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";
			Map<String,Object> urlParams = new HashMap<String,Object>();
			urlParams.put("grant_type", "client_credential");
			urlParams.put("appid", weChatAppId);
			urlParams.put("secret", weChatSecret);
			String tokenResponse = HttpsUtil.doGet(baseUrl, urlParams);
			
			JSONObject tokenJson = JSONObject.parseObject(tokenResponse);
			accessToken.setAccessToken(tokenJson.getString("access_token"));
			accessToken.setExpireIn(tokenJson.getInteger("expires_in"));
			accessToken.setRefreshTime(dateNow);
			accessTokenMapper.updateByPrimaryKey(accessToken);
		}
		
		logger.info("令牌刷新扫描"
				+ ",当前刷新时间："+Utils.formateDate2String(refreshTime, "yyyy-MM-dd HH:mm:ss")
				+ ",当前时间："+Utils.formateDate2String(dateNow, "yyyy-MM-dd HH:mm:ss")
				+",下一次刷新时间："+Utils.formateDate2String(nextRefreshTime, "yyyy-MM-dd HH:mm:ss"));
	}
}
