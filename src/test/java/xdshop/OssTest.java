package xdshop;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.xdshop.vo.OssBaseVo;

public class OssTest {

	/**
	 * 永久URL
	 * @param ossVo
	 * @param ossKey
	 * @return
	 */
	public String getPermanentUrl(OssBaseVo ossVo,String ossKey){
		String url = "";
		String endpoint = ossVo.getEndpoint();
		String bucketName = ossVo.getBucketName();
		//永久URL
		url = "http://"+bucketName+"."+endpoint+"/"+ossKey;
		return url;
	}
	public void uploadFile(OssBaseVo ossVo,String ossKey,InputStream is){
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
	};
	
	public void deleteFile(OssBaseVo ossVo,String ossKey){
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
	};
	
	public String getUrl(OssBaseVo ossVo,String ossKey){
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
		URL url = ossClient.generatePresignedUrl(bucketName, ossKey, expiration);
//		URL url = ossClient.generatePresignedUrl(bucketName, ossKey, null);
		System.out.println("文件URL地址："+url.toString());
		
		ossClient.shutdown();
		return url.toString();
	}
	
	public InputStream getObject(OssBaseVo ossVo,String ossKey) throws IOException{
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
		
		OSSObject ossObject = ossClient.getObject(bucketName, ossKey);
		InputStream is = ossObject.getObjectContent();
		byte[] buf = new byte[1024];
		int length = 0;
		File file = new File("d://获取阿里云.jpg");
		FileOutputStream fos = new FileOutputStream(file);
		while((length = is.read(buf)) > 0){
			fos.write(buf, 0, length);
		}
		fos.flush();
		fos.close();
		ossClient.shutdown();
		return is;
	}
	
	public static void main(String[] args) throws IOException {

		String endpoint = "oss-cn-hangzhou.aliyuncs.com";
		String accessKeyId = "LTAINChIcoUp3q8t";
		String accessKeySecret = "MxTKnqTw2MvegsEmNUrY3ovdWTtFjy";
		String bucketName = "xdshop2018";
		
		OssBaseVo ossVo = new OssBaseVo();
		ossVo.setEndpoint("oss-cn-hangzhou.aliyuncs.com");
		ossVo.setAccessKeyId("LTAINChIcoUp3q8t");
		ossVo.setAccessKeySecret("MxTKnqTw2MvegsEmNUrY3ovdWTtFjy");
		ossVo.setBucketName("xdshop2018");
		
		File file = new File("G:\\xdshop\\微信素材\\分享图背景.jpg");
		FileInputStream fis = new FileInputStream(file);
		String ossKey = "resource/"+file.getName();
		
		OssTest ossTest = new OssTest();
		//上传
		ossTest.uploadFile(ossVo, ossKey,fis);
		//获取URL地址
		String url = ossTest.getUrl(ossVo,ossKey);
		System.out.println("url:"+url);
		
		//永久URL
		String persistUrl = "http://"+bucketName+"."+endpoint+"/"+ossKey;
		System.out.println(persistUrl);
		
	
		//删除文件
//		ossTest.deleteFile(ossVo,ossKey);
		
		//获取文件
		ossTest.getObject(ossVo, ossKey);
	}

}
