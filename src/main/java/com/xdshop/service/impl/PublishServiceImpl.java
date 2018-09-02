package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.api.ShareParamVo;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.domain.Article;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.service.IArticleService;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.vo.OssBaseVo;
import com.xdshop.vo.PublishVo;

import xdshop.OssTest;

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
	
	@Override
	public Publish initPublish() throws Exception {
		Publish publish = new Publish();
		publish.setId(Utils.get16UUID());
		publish.setOpenFlag(false);
		publish.setCreateTime(Calendar.getInstance().getTime());
		publish.setTicketRemain(publish.getTicketTotal());
		publishMapper.insertSelective(publish);
		return publish;
	}

	
	@Override
	public Integer savePublish(PublishVo publish) throws Exception {
		//sql影响记录条数
		int recordNum = 0;
		if(publish.getId() == null){
			publish.setId(Utils.get16UUID());
			publish.setOpenFlag(false);
			recordNum = publishMapper.insertSelective(publish);
		}else{
			recordNum = publishMapper.updateByPrimaryKeySelective(publish);
		}
		
		//保存图文消息
		Article article = publish.getArticle();
		if(article != null)
			articleServiceImpl.saveArticle(article);
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

		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		String accessKeyId = "LTAINChIcoUp3q8t";
		String accessKeySecret = "MxTKnqTw2MvegsEmNUrY3ovdWTtFjy";
		String bucketName = "xdshop2018";
		
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint("oss-cn-hangzhou.aliyuncs.com");
		ossVo.setAccessKeyId("LTAINChIcoUp3q8t");
		ossVo.setAccessKeySecret("MxTKnqTw2MvegsEmNUrY3ovdWTtFjy");
		ossVo.setBucketName("xdshop2018");
		
		String ossKey = "resource/"+fileName;
		
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
		String sharePicUrl = "http://xdshop2018.oss-cn-hangzhou.aliyuncs.com/resource/分享图背景.jpg";
		logger.info("准备生成用户分享图片："+JSONObject.toJSONString(shareParamVo));
		/**
		 * 对接微信接口，注入相关信息到底图
		 */
		
		return sharePicUrl;
	}

}
