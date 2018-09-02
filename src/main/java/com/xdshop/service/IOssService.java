package com.xdshop.service;

import java.io.InputStream;

import com.xdshop.vo.OssBaseVo;

public interface IOssService {
	public void uploadFile(OssBaseVo ossVo,String ossKey,InputStream is) throws Exception;
	
	public void deleteFile(OssBaseVo ossVo,String ossKey) throws Exception;
	
	public String getUrl(OssBaseVo ossVo,String ossKey) throws Exception;
	
	public String getPermanentUrl(OssBaseVo ossVo,String ossKey) throws Exception;
}
