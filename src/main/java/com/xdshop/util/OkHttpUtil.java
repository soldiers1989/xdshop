package com.xdshop.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * OkHttp方法 查询pom网址为 http://mvnrepository.com/ 引用pom包为 <dependency> <groupId>com.squareup.okhttp</groupId> <artifactId>okhttp</artifactId> <version>2.7.5</version> </dependency>
 *
 * @author XHL on 2018/2/24
 */
public class OkHttpUtil {
	private static final Logger logger = Logger.getLogger(OkHttpUtil.class);
	private static OkHttpClient client = new OkHttpClient();

	static {
		// 超时配置
		client.setConnectTimeout(30, TimeUnit.SECONDS);
		client.setReadTimeout(30, TimeUnit.SECONDS);
	}
	// 拼接请求头

	/**
	 * get方法带头信息
	 *
	 * @param url
	 *            路径
	 * @param headers
	 *            请求头
	 * @return 返回结果
	 * @throws IOException
	 */
	public static Response get(String url, Map<String, String> params) throws Exception {
		logger.info("GET请求，地址："+url);
		logger.info("GET请求，参数：");
		logger.info(params);
		Response response = null;
		Request request;
		Request.Builder builder = new Request.Builder();
		HttpUrl httpUrl = HttpUrl.parse(url);
		if (params != null) {
			for (String key : params.keySet()) {
				httpUrl = httpUrl.newBuilder().addQueryParameter(key, params.get(key)).build();
			}
		}
		logger.info("GET请求,组装后地址："+httpUrl.toString());
		builder.header("Content-Type", "application/json;charset=UTF-8");
		request = builder.url(httpUrl).get().build();
		response = client.newCall(request).execute();
		logger.info("GET请求，返回对象：");
		logger.info(response);
		return response;
	}

	public static String getString(String url, Map<String, String> params) throws Exception {
		String result = null;
		Response response = get(url, params);
		result = response.body().string();
		logger.info("GET请求，返回值："+result);
		return result;
	}

	/**
	 * post方法带头信息
	 *
	 * @param url
	 *            路径
	 * @param headers
	 *            请求头
	 * @return 返回结果
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> param) throws IOException {
		logger.info("POST请求，地址："+url);
		logger.info("POST请求，参数：");
		logger.info(param);
		String result = null;
		RequestBody formBody = getFormEncodingBuilder(param).build();
		Request request;
		Request.Builder builder = new Request.Builder();
		builder.header("Content-Type", "application/json;charset=UTF-8");
		request = builder.url(url).post(formBody).build();
		result = client.newCall(request).execute().body().string();
		logger.info("POST请求，HTTP响应内容："+result);
		return result;
	}

	/**
	 * post方法提供参数
	 *
	 * @param param
	 *            参数列表
	 * @return FormEncodingBuilder
	 */
	private static FormEncodingBuilder getFormEncodingBuilder(Map<String, String> param) {
		FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			formEncodingBuilder.add(entry.getKey(), entry.getValue());
		}
		return formEncodingBuilder;
	}

}
