package xdshop;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xdshop.util.HttpsUtil;

/**
 * https 通信测试
 * @author 160068
 * 2018年8月22日 下午1:53:16
 */
public class HttpsTest {

	/**
	 * 测试HTTPS GET请求 
	 * 查询天气
	 * 160068
	 * 2018年8月22日 下午1:55:03
	 */
	public void doGet() {
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("city", "重庆");
		String res = HttpsUtil.doGet("https://www.sojson.com/open/api/weather/json.shtml", urlParams);
	}
	
	/**
	 * 测试HTTPS POST请求 
	 * 高德地图，创建地理围栏API服务
	 * 160068
	 * 2018年8月22日 下午1:55:36
	 */
	public void doPost() {
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("key", "用户key");
		
		Map<String,Object> bodyParams = new HashMap<String,Object>();
		bodyParams.clear();
		bodyParams.put("name", "围栏名称");
		System.out.println("body："+JSONObject.toJSONString(bodyParams));
//		HttpsUtil.doPost("https://restapi.amap.com/v4/geofence/meta", urlParams, bodyParams);
		HttpsUtil.doPostJson("https://restapi.amap.com/v4/geofence/meta", urlParams, bodyParams);
	}
	
	public static void main(String[] args) {
		HttpsTest ht = new HttpsTest();
		ht.doGet();
		ht.doPost();
	}

}
