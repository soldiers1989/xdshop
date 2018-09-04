package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;
import com.xdshop.dal.domain.PublishResource;

public interface IPublishResourceService {
	
	public List<PublishResource> getPublishResourceList(PublishResource publishResource) throws Exception;
	
	public String getOssKeyByPublishIdAndUrl(String publishId,String url) throws Exception;
	
}
