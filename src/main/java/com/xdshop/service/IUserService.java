package com.xdshop.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import com.xdshop.api.FetchUserParam;
import com.xdshop.api.PublishUserParam;
import com.xdshop.dal.domain.User;
import com.xdshop.dal.domain.UserShare;
import com.xdshop.vo.ResponseVo;
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
	List<HashMap<String,Object>> getFetchUser(FetchUserParam fetchUserParam) throws Exception;
	
	public List<HashMap<String,Object>> getPublishUser(PublishUserParam param) throws Exception;
	/**
	 * 取票
	 * @param publishId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public ResponseVo fetch(String publishId,String openId) throws Exception;
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
	
	public UserShare getUserShareInfo(String publishId,String openId) throws Exception;
	/**
	 * 获取门票领用状态
	 * 160068
	 * 2018年9月21日 下午4:23:47
	 * @param publishId
	 * @param openId
	 * @return 已领取：true；未领取：false
	 * @throws Exception
	 */
	public Boolean getFetchStatus(String publishId,String openId) throws Exception;
	
	
	
}
