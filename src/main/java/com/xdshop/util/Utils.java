package com.xdshop.util;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.Assert;

import com.squareup.okhttp.HttpUrl;

public class Utils {
	
	
	/**
	 * 获取GET请求URL地址（url地址后跟参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public String getUrl(String url, Map<String, String> params){
		HttpUrl httpUrl = HttpUrl.parse(url);
		if (params != null) {
			for (String key : params.keySet()) {
				httpUrl = httpUrl.newBuilder().addQueryParameter(key, params.get(key)).build();
			}
		}
//		logger.info("GET请求,组装后地址："+httpUrl.toString());
		return httpUrl.toString();
	}
	
	/************************************历史记录相关开始*****************************************/
	/**
	 * 对象属性转换为对应数据表列名
	 * 
	 * @param propName
	 *            输入格式："myUserName"
	 * @return 返回格式：：MY_USER_NAME
	 */
	public static String field2Col(String propName) {
		// System.out.println("对象属性转换前："+propName);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < propName.length(); i++) {
			char c = propName.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append("_" + Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString().toUpperCase();
	}
	
	/**
	 * 递归所有父类field
	 * 
	 * @param obj
	 *            当前递归对象
	 * @param fieldList
	 *            所有field列表
	 */
	public static void getField(Class obj, List<Field> fieldList) {
		Field[] fields = obj.getDeclaredFields();
		if (!obj.getName().equals("java.lang.Object")) {
			for (Field field : fields) {
				field.setAccessible(true);
				fieldList.add(field);
			}
			Utils.getField(obj.getSuperclass(), fieldList);
		}
	}
	
	/**
	 * 获取对象所有field
	 * 
	 * @param obj
	 * @return
	 */
	public static List<Field> getFieldList(Class obj) {
		List<Field> fieldList = new LinkedList<Field>();
		Utils.getField(obj, fieldList);
		return fieldList;
	}
	
	/**
	 * @param fieldName
	 *            属性名
	 * @return 属性对应set方法
	 */
	public static String field2SetMethod(String fieldName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("set");
		buffer.append(fieldName.substring(0, 1).toUpperCase());
		buffer.append(fieldName.substring(1, fieldName.length()));
		return buffer.toString();
	}
	
	/**
	 * 对应数据表列名转对象属性
	 * 
	 * @param colName
	 *            输入格式："my_col_name"
	 * @return 返回格式：：myColName
	 */
	public static String col2Field(String colName) {
		StringBuffer fieldNameBuf = new StringBuffer();
		String[] colNames = colName.split("_");
		for (int i = 0; i < colNames.length; i++) {
			String temp = colNames[i];
			if (i == 0)
				fieldNameBuf.append(temp);
			else {
				fieldNameBuf.append(temp.substring(0, 1).toUpperCase());
				fieldNameBuf.append(temp.substring(1, temp.length()));
			}
		}
		return fieldNameBuf.toString();
	}
	/************************************历史记录相关开始*****************************************/
	
	
	public static String get16UUID() {
		String uuid = UUID.randomUUID().toString();
		byte[] outputByteArray;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] inputByteArray = uuid.getBytes();
			messageDigest.update(inputByteArray);
			outputByteArray = messageDigest.digest();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < outputByteArray.length; offset++) {
			int i = outputByteArray[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().substring(8, 24);
	}
	
	public static Date getDateAfterDay(Date date,int interval){
		String afterYear = "";
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DAY_OF_MONTH, interval);
		return calender.getTime();
	}
	
	/**
	 * 字符串转日期
	 * tom 2016年11月7日
	 * @param date
	 * @param formateStr
	 * @return
	 */
	public static Date formateString2Date(String date,String formateStr){
		SimpleDateFormat formate = new SimpleDateFormat(formateStr);
		Date dateRet = null;
		try {
			dateRet = formate.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateRet;
	}
	
	/**
	 * 日期转字符串
	 * tom 2016年11月7日
	 * @param date
	 * @param formateStr
	 * @return
	 */
	public static String formateDate2String(Date date,String formateStr){
		SimpleDateFormat formate = new SimpleDateFormat(formateStr);
		String dateRet = "";
		try {
			dateRet = formate.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateRet;
	}
	
	/**
	 * 校验GPS编码格式
	 * @param gpsId
	 */
	public static void validGpsId(String gpsId){
		Pattern pattern = Pattern.compile("^[a-z0-9A-Z]{10,20}$");
		Matcher matcher = pattern.matcher(gpsId);
		Assert.isTrue(matcher.matches(),"导入失败！GPS编码【"+gpsId+"】不合法,只能输入数字[0-9]、字母[a-zA-Z],最小位数：10位");
		Pattern pattern2 = Pattern.compile("(\\w)(\\1){10}");
        Matcher matcher2 = pattern2.matcher(gpsId);
        Assert.isTrue(!matcher2.find(), "导入失败！GPS编码【"+gpsId+"】不能为同一个字符");
	}
	
	public static String MD5(String s) {
	    try {
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        byte[] bytes = md.digest(s.getBytes("utf-8"));
	        return toHex(bytes);
	    }
	    catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

	public static String toHex(byte[] bytes) {

	    final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
	    StringBuilder ret = new StringBuilder(bytes.length * 2);
	    for (int i=0; i<bytes.length; i++) {
	        ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
	        ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
	    }
	    return ret.toString();
	}
	
}
