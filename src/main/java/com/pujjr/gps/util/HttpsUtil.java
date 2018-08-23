package com.pujjr.gps.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.log4j.Logger;

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
            conn.setDoOutput(true);
            logger.info("POST请求,url参数："+urlParams.toString());
            logger.info("POST请求,body参数："+postBody.toString());
            logger.info("POST请求，准备发送请求...");
            conn.getOutputStream().write(postBody.toString().getBytes());
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
}
