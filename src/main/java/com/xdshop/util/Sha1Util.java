package com.xdshop.util;

import java.security.DigestException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Sha1Util {
	private static final Logger logger = Logger.getLogger(Sha1Util.class);
	/**
	 * SHA1 安全加密算法
	 * @param maps 参数key-value map集合
	 * @return
	 * @throws DigestException 
	 */
	public static String sha1Encryt(List<String> decryptList) throws DigestException {
		//获取信息摘要 - 参数字典排序后字符串
//		String decrypt = getOrderByLexicographic(maps);
//		String decrypt = "15348691381725671455xdshop";
		String decrypt = getOrderValue(decryptList);
		try {
			//指定sha1算法
//			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			MessageDigest digest = DigestUtils.getSha1Digest();
			digest.update(decrypt.getBytes());
			//获取字节数组
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DigestException("签名错误！");
		}
	}
	
	public static String getOrderValue(List<String> decryptList) {
		String orderStr = "";
		Collections.sort(decryptList);
		StringBuilder sb = new StringBuilder();
		for (String tempStr : decryptList) {
			sb.append(tempStr);
		}
		orderStr = sb.toString();
		logger.info("排序后待加密字符串："+orderStr);
		return orderStr;
		
	}
	
	/**
	 * 获取参数的字典排序
	 * @param maps 参数key-value map集合
	 * @return String 排序后的字符串
	 */
	private static String getOrderByLexicographic(Map<String,Object> maps){
		return splitParams(lexicographicOrder(getParamsName(maps)),maps);
	}
	/**
	 * 获取参数名称 key
	 * @param maps 参数key-value map集合
	 * @return
	 */
	private static List<String> getParamsName(Map<String,Object> maps){
		List<String> paramNames = new ArrayList<String>();
		for(Map.Entry<String,Object> entry : maps.entrySet()){
			paramNames.add(entry.getKey());
		}
		return paramNames;
	}
	/**
	 * 参数名称按字典排序
	 * @param paramNames 参数名称List集合
	 * @return 排序后的参数名称List集合
	 */
	private static List<String> lexicographicOrder(List<String> paramNames){
		Collections.sort(paramNames);
		return paramNames;
	}
	/**
	 * 拼接排序好的参数名称和参数值
	 * @param paramNames 排序后的参数名称集合
	 * @param maps 参数key-value map集合
	 * @return String 拼接后的字符串
	 */
	private static String splitParams(List<String> paramNames,Map<String,Object> maps){
		StringBuilder paramStr = new StringBuilder();
		for(String paramName : paramNames){
			logger.info("当前获取参数名paramName："+paramName);
//			paramStr.append(paramName);
			for(Map.Entry<String,Object> entry : maps.entrySet()){
				if(paramName.equals(entry.getKey())){
					paramStr.append(String.valueOf(entry.getValue()));
				}
			}
		}
		return paramStr.toString();
	}
	
	/**
	 * 验证签名
	 * 160068
	 * 2018年8月21日 下午1:56:24
	 * @param signature 收到的签名
	 * @param decryptMap 收到的参数组成的消息摘要数组
	 * @return 签名是否合法
	 * @throws Exception
	 */
	public static boolean verifySinature(String signature,List<String> decryptList) throws Exception {
		boolean verifyRet = false;
		String realSinature = Sha1Util.sha1Encryt(decryptList);
		if(signature.equals(realSinature)) {
			logger.info("验签成功");
			verifyRet = true;
		}else {
			logger.info("验签失败，收到签名："+signature+"与实际签名："+realSinature+"不符");
			verifyRet = false;
		}
		return verifyRet;
	}
}
