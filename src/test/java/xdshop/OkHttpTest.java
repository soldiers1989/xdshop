package xdshop;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import com.xdshop.util.OkHttpUtil;

public class OkHttpTest {

	public static void main(String[] args) throws Exception {
		String url = "http://47.98.40.54/xdshop";
		Map<String,String> params = new HashedMap();
		params.put("signature", "fd8664638cb9d09ba44f5c19d86bdbc5103ecc49");
		params.put("timestamp", "1534871635");
		params.put("nonce", "1950090967");
		params.put("echostr", "2493388384202750579");
//		Response response = OkHttpUtil.get(url, params);
//		System.out.println(response.body().toString());
		String result = OkHttpUtil.getString(url, params);
	}

}
