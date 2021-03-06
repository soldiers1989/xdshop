package com.xdshop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.xdshop.service.IOssService;
import com.xdshop.util.Utils;
import com.xdshop.vo.OssBaseVo;


@Service
public class OssServiceImpl implements IOssService {
	private static final Logger logger = Logger.getLogger(OssServiceImpl.class);
	@Override
	public void uploadFile(OssBaseVo ossVo, String ossKey, InputStream is) throws Exception {
		String endpoint = ossVo.getEndpoint();
		String accessKeyId = ossVo.getAccessKeyId();
		String accessKeySecret = ossVo.getAccessKeySecret();
		String bucketName = ossVo.getBucketName();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		if (ossClient.doesBucketExist(bucketName)) {
			System.out.println("您已经创建Bucket：" + bucketName + "。");
		} else {
			System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
			ossClient.createBucket(bucketName);
		}
		
		PutObjectResult por = ossClient.putObject(bucketName, ossKey, is);
		System.out.println(por);
		ossClient.shutdown();
		
	}
	@Override
	public void deleteFile(OssBaseVo ossVo, String ossKey) throws Exception {
		String endpoint = ossVo.getEndpoint();
		String accessKeyId = ossVo.getAccessKeyId();
		String accessKeySecret = ossVo.getAccessKeySecret();
		String bucketName = ossVo.getBucketName();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		if (ossClient.doesBucketExist(bucketName)) {
			System.out.println("您已经创建Bucket：" + bucketName + "。");
		} else {
			System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
			ossClient.createBucket(bucketName);
		}
		ossClient.deleteObject(bucketName, ossKey);
		ossClient.shutdown();
		
	}
	@Override
	public String getUrl(OssBaseVo ossVo, String ossKey) throws Exception {
		String endpoint = ossVo.getEndpoint();
		String accessKeyId = ossVo.getAccessKeyId();
		String accessKeySecret = ossVo.getAccessKeySecret();
		String bucketName = ossVo.getBucketName();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
		if (ossClient.doesBucketExist(bucketName)) {
			System.out.println("您已经创建Bucket：" + bucketName + "。");
		} else {
			System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
			ossClient.createBucket(bucketName);
		}
		
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		URL url = ossClient.generatePresignedUrl(bucketName, ossKey, null);
		System.out.println("文件URL地址："+url.toString());
		
		ossClient.shutdown();
		return url.toString();
	}
	
	/**
	 * 永久URL
	 * @param ossVo
	 * @param ossKey
	 * @return
	 */
	@Override
	public String getPermanentUrl(OssBaseVo ossVo,String ossKey){
		String url = "";
		String endpoint = ossVo.getEndpoint();
		String bucketName = ossVo.getBucketName();
		ossKey = Utils.strEncoder(ossKey);
		//永久URL
		url = "http://"+bucketName+"."+endpoint+"/"+ossKey;
		return url;
	}
	@Override
	public InputStream getObject(OssBaseVo ossVo,String ossKey) throws IOException{
		String endpoint = ossVo.getEndpoint();
		String accessKeyId = ossVo.getAccessKeyId();
		String accessKeySecret = ossVo.getAccessKeySecret();
		String bucketName = ossVo.getBucketName();
		
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, ossKey);
		InputStream is = ossObject.getObjectContent();
		
		return is;
	}
	
	

}
