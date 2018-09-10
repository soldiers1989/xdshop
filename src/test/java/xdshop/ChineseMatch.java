package xdshop;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChineseMatch {

	/**
	 * url转码
	 * 160068
	 * 2018年9月10日 下午1:56:51
	 * @param str
	 * @return
	 */
	public String urlEncoder(String str) {
//		String str = "http://xdshop2018.oss-cn-hangzhou.aliyuncs.com/resource/o_eRa0_ilXHtq7bTo0RyRhC8LieA/ce2434189eab1957/分享海报.jpg";
		StringBuffer urlSb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String tempStr = str.substring(i, i+1);
			boolean isMatch = tempStr.matches("[\u4e00-\u9fa5]+");
			System.out.println(tempStr+"|"+isMatch);
			System.out.println("url转码");
			if(isMatch) {
				try {
					tempStr = URLEncoder.encode(tempStr, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				System.out.println(tempStr);
			}
			urlSb.append(tempStr);
		}
		return urlSb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		String str = "http://xdshop2018.oss-cn-hangzhou.aliyuncs.com/resource/o_eRa0_ilXHtq7bTo0RyRhC8LieA/ce2434189eab1957/分享海报.jpg";
		StringBuffer urlSb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			String tempStr = str.substring(i, i+1);
			boolean isMatch = tempStr.matches("[\u4e00-\u9fa5]+");
			System.out.println(tempStr+"|"+isMatch);
			System.out.println("url转码");
			if(isMatch) {
				tempStr = URLEncoder.encode(tempStr, "UTF-8");
				System.out.println(tempStr);
			}
			urlSb.append(tempStr);
		}
		str = urlSb.toString();
		System.out.println(str);
		
	}

}
