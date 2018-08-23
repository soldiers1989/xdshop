package com.pujjr.gps.schedule;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pujjr.gps.dal.dao.AccessTokenMapper;
import com.pujjr.gps.dal.domain.AccessToken;
import com.pujjr.gps.service.base.BaseService;
import com.pujjr.gps.util.HttpsUtil;
import com.pujjr.gps.util.Utils;

@Service
@EnableScheduling
public class AccessTokenScheduler extends BaseService<AccessTokenScheduler> {
	@Autowired
	private AccessTokenMapper accessTokenMapper;
	private static final int REFRESH_DURE = 60;
	
	@Scheduled(cron = "0 */1 * * * ?") 
	public void autoUploadAllDataToSeg() {
		AccessToken accessToken = accessTokenMapper.selectByPrimaryKey("1");
		Date refreshTime = accessToken.getRefreshTime();
		Calendar nextRefreshCl = Calendar.getInstance();
		nextRefreshCl.setTime(refreshTime);
		nextRefreshCl.add(Calendar.MINUTE,REFRESH_DURE);
		Date nextRefreshTime = nextRefreshCl.getTime();
		Date dateNow = Calendar.getInstance().getTime();
		logger.info("令牌超时校验"
				+ ",当前刷新时间："+Utils.formateDate2String(refreshTime, "yyyy-MM-dd HH:mm:ss")
				+ ",当前时间："+Utils.formateDate2String(dateNow, "yyyy-MM-dd HH:mm:ss")
				+",下一次刷新时间："+Utils.formateDate2String(nextRefreshTime, "yyyy-MM-dd HH:mm:ss"));
		//到达下一次刷新时间
		if(nextRefreshTime.before(dateNow)) {
			logger.info("刷新AccessToken");
			
			String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";
			Map<String,Object> urlParams = new HashMap<String,Object>();
			urlParams.put("grant_type", "client_credential");
			urlParams.put("appid", "wxcc4c443d5a08ed7d");
			urlParams.put("secret", "89ece1ca08163acc1737155060b33eb1");
			HttpsUtil.doGet(baseUrl, urlParams);
			
			accessToken.setAccessToken(dateNow.getTime()+"");
			accessToken.setRefreshTime(dateNow);
			accessTokenMapper.updateByPrimaryKey(accessToken);
		}
	}
}
