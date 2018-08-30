package com.xdshop.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class HttpsUtil {
	private static final Logger logger = Logger.getLogger(HttpsUtil.class);
/*	@Value("${submit_url}")
	private String submit_url;
	@Value("${query_url}")
	private String query_url;
	@Value("${partner_code}")
	private String partner_code;
	@Value("${partner_key}")
	private String partner_key;
	@Value("${app_name}")
	private String app_name;
	@Value("${wait_time}")
	private long wait_time;*/
	
	
	
	public static String doPost(String baseUrl,Map<String, Object> urlParams,Map<String, Object> bodyParams) {
		logger.info("*************POST请求开始*************");
		HttpsURLConnection conn = null;
	    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		String res = "POST请求，无返回";
        try {
        	StringBuilder urlSb = new StringBuilder();
//          String urlString = new StringBuilder().append(submit_url).append("?partner_code=").append(partner_code).append("&partner_key=").append(partner_key).append("&app_name=").append(app_name).toString();
        	String urlString = "";
        	urlSb.append(baseUrl).append("?");
            
        	for (Map.Entry<String, Object> entry : urlParams.entrySet()) {
                if (entry.getValue() == null) continue;
                	urlSb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
        	urlString = urlSb.toString();
        	URL url = new URL(urlString);
            
            logger.info("POST请求，地址："+urlString);
            
            // 组织请求参数
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
                if (entry.getValue() == null) continue;
                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
            if (!bodyParams.isEmpty()) {
                postBody.deleteCharAt(postBody.length() - 1);
            }
            conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(500);
            // 提交参数
            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setDoOutput(true);
            logger.info("POST请求,url参数："+urlParams.toString());
            logger.info("POST请求,body参数："+postBody.toString());
            logger.info("POST请求，准备发送请求...");
//            conn.getOutputStream().write(postBody.toString().getBytes());
            conn.getOutputStream().flush();
            int responseCode = conn.getResponseCode();
            logger.info("POST请求，返回状态码："+responseCode);
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                res = result.toString().trim();
                logger.info("POST请求，返回值："+res);
            }
        } catch (Exception e) {
            logger.error("[RiskServicePreloan] apply throw exception, details: " + e);
            e.printStackTrace();
        }
        logger.info("*************POST请求结束*************");
        return res;
	}
	
	
	public static String doPostJson(String baseUrl,Map<String, Object> urlParams,Map<String, Object> bodyParams) {
		logger.info("*************POST请求开始*************");
		HttpsURLConnection conn = null;
	    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		String res = "POST请求，无返回";
        try {
        	StringBuilder urlSb = new StringBuilder();
//          String urlString = new StringBuilder().append(submit_url).append("?partner_code=").append(partner_code).append("&partner_key=").append(partner_key).append("&app_name=").append(app_name).toString();
        	String urlString = "";
        	urlSb.append(baseUrl).append("?");
            
        	for (Map.Entry<String, Object> entry : urlParams.entrySet()) {
                if (entry.getValue() == null) continue;
                	urlSb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
        	urlString = urlSb.toString();
        	URL url = new URL(urlString);
            
            logger.info("POST请求，地址："+urlString);
            
            // 组织请求参数
            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : bodyParams.entrySet()) {
                if (entry.getValue() == null) continue;
                postBody.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
            if (!bodyParams.isEmpty()) {
                postBody.deleteCharAt(postBody.length() - 1);
            }
            conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(5000);
            // 提交参数
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setDoOutput(true);
            logger.info("POST请求,url参数："+urlParams.toString());
            logger.info("POST请求,body参数："+postBody.toString());
            logger.info("POST请求，准备发送请求...");
            String jsonToSend = JSONObject.toJSONString(bodyParams);
//            conn.getOutputStream().write(jsonToSend.getBytes());
            logger.info("发送json："+jsonToSend);
//            conn.getOutputStream().write(jsonToSend.getBytes());
            
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());  
            out.write(jsonToSend.getBytes("UTF-8")); 
            
            out.flush();
            int responseCode = conn.getResponseCode();
            logger.info("POST请求，返回状态码："+responseCode);
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                res = result.toString().trim();
                logger.info("POST请求，返回值："+res);
            }
        } catch (Exception e) {
            logger.error("[RiskServicePreloan] apply throw exception, details: " + e);
            e.printStackTrace();
        }
        logger.info("*************POST请求结束*************");
        return res;
	}
	
	public static String  doGet(String baseUrl,Map<String, Object> urlParams) {
		logger.info("*************GET请求开始*************");
		HttpsURLConnection conn = null;
	    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		String res = "GET请求，无返回";
        try {
        	StringBuilder urlSb = new StringBuilder();
//	        String urlString = new StringBuilder().append(query_url).append("?partner_code=").append(partner_code).append("&partner_key=").append(partner_key).append("&report_id=").append(report_id).toString();
        	String urlString = "";
        	urlSb.append(baseUrl).append("?");
            
         // 组织请求参数
//            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : urlParams.entrySet()) {
                if (entry.getValue() == null) continue;
                urlSb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
            urlString = urlSb.toString();
            URL url = new URL(urlString);
            
            logger.info("GET请求，地址："+urlString);
            
            conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(3000);
            // 提交参数
            conn.setRequestMethod("GET");
            logger.info("GET请求，准备发送请求...");
            int responseCode = conn.getResponseCode();
            logger.info("GET请求，返回状态码："+responseCode);
            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line).append("\n");
                }
                res = result.toString().trim();
                logger.info("GET请求，返回值："+res);
            }
        } catch (Exception e) {
            logger.error("[RiskServicePreloan] query throw exception, details: " + e);
            e.printStackTrace();
        }
        logger.info("*************GET请求结束*************");
        return res;
	}
	
	public static byte[]  doGetByte(String baseUrl,Map<String, Object> urlParams) {
		logger.info("*************GET请求开始*************");
		byte[] byteRet = null;
		HttpsURLConnection conn = null;
	    SSLSocketFactory ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		String res = "GET请求，无返回";
        try {
        	StringBuilder urlSb = new StringBuilder();
//	        String urlString = new StringBuilder().append(query_url).append("?partner_code=").append(partner_code).append("&partner_key=").append(partner_key).append("&report_id=").append(report_id).toString();
        	String urlString = "";
        	urlSb.append(baseUrl).append("?");
            
         // 组织请求参数
//            StringBuilder postBody = new StringBuilder();
            for (Map.Entry<String, Object> entry : urlParams.entrySet()) {
                if (entry.getValue() == null) continue;
                urlSb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue().toString(),"utf-8")).append("&");
            }
            urlString = urlSb.toString();
            URL url = new URL(urlString);
            
            logger.info("GET请求，地址："+urlString);
            
            conn = (HttpsURLConnection) url.openConnection();
            //设置https
            conn.setSSLSocketFactory(ssf);
            // 设置长链接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置连接超时
            conn.setConnectTimeout(1000);
            // 设置读取超时
            conn.setReadTimeout(3000);
            // 提交参数
            conn.setRequestMethod("GET");
            logger.info("GET请求，准备发送请求...");
            int responseCode = conn.getResponseCode();
            logger.info("GET请求，返回状态码："+responseCode);
            if (responseCode == 200) {
            	InputStream is = conn.getInputStream();
            	BufferedInputStream bis = new BufferedInputStream(is);
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();
            	
//            	DataInputStream dis = new DataInputStream(is);
            	byte[] buf = new byte[10240];
            	int length = 0;
            	while((length = bis.read(buf)) > 0){
//            		System.out.println(length);
            		baos.write(buf, 0, length);
            	} 
            	
            	byteRet = baos.toByteArray();
            	logger.info("GET请求，返回值："+byteRet.length);
            	return byteRet;
            	
                
            }
        } catch (Exception e) {
            logger.error("[RiskServicePreloan] query throw exception, details: " + e);
            e.printStackTrace();
        }
        logger.info("*************GET请求结束*************");
        return byteRet;
	}
}
