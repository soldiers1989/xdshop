package xdshop;

import java.util.HashMap;
import java.util.Map;

import com.xdshop.util.HttpsUtil;

public class AccessTokenTest {

	
	
	public static void main(String[] args) {
//		String appId = "wxe446196f5726d322";
//		String secret = "9be409fa349f1cd84e42dcddcff64703";
		
		//重庆旅游生活宝
		String appId = "wx55980339ef1cf5bd";
		String secret = "f36026086495e7e1658beb231440f6c4";
		
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("grant_type", "client_credential");
		urlParams.put("appid", appId);
		urlParams.put("secret", secret);
		HttpsUtil.doGet(baseUrl, urlParams);

	}

}
