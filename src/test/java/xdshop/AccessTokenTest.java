package xdshop;

import java.util.HashMap;
import java.util.Map;

import com.pujjr.gps.util.HttpsUtil;

public class AccessTokenTest {

	
	
	public static void main(String[] args) {
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/token";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("grant_type", "client_credential");
		urlParams.put("appid", "wxcc4c443d5a08ed7d");
		urlParams.put("secret", "89ece1ca08163acc1737155060b33eb1");
		HttpsUtil.doGet(baseUrl, urlParams);

	}

}
