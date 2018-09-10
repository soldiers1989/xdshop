package com.xdshop.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.xdshop.api.PublishUserParam;
import com.xdshop.dal.domain.User;
import com.xdshop.vo.UserInfoVo;

public interface IUserService {
	
	public ByteArrayInputStream getHeaderImg(String headerUrl) throws Exception;
	
	public UserInfoVo getUserInfo(String openId,String accessToken) throws Exception;
	
	public InputStream getQrCode(String openId,String accessToken) throws Exception;
	
	public List<User> getSubUser(String publishId,String openId) throws Exception;
	/**
	 * 获取已领取成功客户列表
	 * @param publisId
	 * @return
	 * @throws Exception
	 */
	List<HashMap<String,Object>> getFetchUser(String publisId) throws Exception;
	
	public List<HashMap<String,Object>> getPublishUser(PublishUserParam param) throws Exception;
	/**
	 * 取票
	 * @param publishId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public int fetch(String publishId,String openId) throws Exception;
	/**
	 * 取消取票
	 * @param publishId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public int unfetch(String publishId,String openId) throws Exception;
	
	/**
	 * 获取客户分享海报
	 * @param publishId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public String getPosterOssUrl(String publishId,String openId) throws Exception;
	
	public User getUserInfo(String openId) throws Exception;
	
	
	
}
