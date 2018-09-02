package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.xdshop.api.BaseParam;
import com.xdshop.dal.dao.PublishMapper;
import com.xdshop.dal.dao.PublishResourceMapper;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;
import com.xdshop.service.IOssService;
import com.xdshop.service.IPublishService;
import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;
import com.xdshop.util.Utils;
import com.xdshop.vo.OssBaseVo;

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
	
	@Override
	public Publish initPublish() throws Exception {
		Publish publish = new Publish();
		publish.setId(Utils.get16UUID());
		publishMapper.insertSelective(publish);
		return publish;
	}

	
	@Override
	public Integer savePublish(Publish publish) throws Exception {
		//sql影响记录条数
		int recordNum = 0;
		if(publish.getId() == null){
			publish.setId(Utils.get16UUID());
			recordNum = publishMapper.insertSelective(publish);
		}else{
			recordNum = publishMapper.updateByPrimaryKeySelective(publish);
		}
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

}
