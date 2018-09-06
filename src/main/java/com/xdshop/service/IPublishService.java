package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.domain.Publish;
import com.xdshop.vo.PublishVo;

public interface IPublishService {
	
	public Publish initPublish() throws Exception;
	
	public Integer savePublish(PublishVo publish) throws Exception;
	
	public Integer openPublish(PublishVo publish) throws Exception;
	
	public List<Publish> getPublishList(BaseParam baseParam) throws Exception;
	
	public void uploadResource(MultipartFile file,String typeCode,String publishId) throws Exception;
	
	public Publish getPublish(String id) throws Exception;
	
	/**
	 * 生成分享图片
	 * @param shareParamVo
	 * @return 生成的分享图片链接地址
	 * @throws Exception
	 */
	public String generalSharePic(ShareParamVo shareParamVo) throws Exception;
	/**
	 * 获取当前活动
	 * @return
	 * @throws Exception
	 */
	public Publish getCurrPublish() throws Exception;
	
	/**
	 * 生成首次分享图片（推送给关注公众号的客户）
	 * @param shareParamVo
	 * @return 生成的分享图片链接地址
	 * @throws Exception
	 */
	public Publish generalFistSharePic(String publishId) throws Exception;
	
}
