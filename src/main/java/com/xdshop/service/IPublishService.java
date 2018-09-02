package com.xdshop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.xdshop.api.BaseParam;
import com.xdshop.dal.domain.Publish;

public interface IPublishService {
	
	public Publish initPublish() throws Exception;
	
	public Integer savePublish(Publish publish) throws Exception;
	
	public List<Publish> getPublishList(BaseParam baseParam) throws Exception;
	
	public void uploadResource(MultipartFile file,String typeCode,String publishId) throws Exception;
	
	public Publish getPublish(String id) throws Exception;
	
}
