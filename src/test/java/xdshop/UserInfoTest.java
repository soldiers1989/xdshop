package xdshop;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.http.client.domain.UserInfo;
import com.squareup.okhttp.Response;
import com.xdshop.util.HttpsUtil;
import com.xdshop.util.OkHttpUtil;
import com.xdshop.vo.UserInfoVo;

/**
 * 获取用户信息
 * @author Administrator
 *
 */
public class UserInfoTest {

	/**
	 * 获取头像字节数组
	 * @param userInfoVo
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayInputStream getHeaderImg(UserInfoVo userInfoVo) throws Exception{
		String headerImgUrl = userInfoVo.getHeadimgurl();
		Map<String,String> params = new HashMap<String,String>();
		Response reponse = OkHttpUtil.get(headerImgUrl, params);
		byte [] headerImgByteArray = reponse.body().bytes();
		
		File file = new File("d:\\header.jpg");
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		
		fos.write(headerImgByteArray, 0, headerImgByteArray.length);
		fos.flush();
		fos.close();
		return new ByteArrayInputStream(headerImgByteArray);
	}
	/**
	 * 获取用户信息
	 * @param openId
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public static UserInfoVo getUserInfo(String openId,String accessToken) throws Exception{
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("access_token", accessToken);
		urlParams.put("openid", openId);
		String userInfoStr = HttpsUtil.doGet(baseUrl, urlParams);
		UserInfoVo userInfoVo = JSONObject.parseObject(userInfoStr, UserInfoVo.class);
		return userInfoVo;
	}
	
	public static void main(String[] args) throws Exception {
		String accessToken = "13_RtyPPPq5znaNWKzTWvOjH_oqk4psA3TS8Q4WRlQbC56l9lzs9lCDm-wUeCGCq9goJSQbgl-eOcjkplfGYFXGP8-4q7NGObBl4a-HeVUUf_qGoaLo6U_wUUu1EVRUlMNqeOjpaOdQoEc0VOSNZIIhAHAUDD";
		String openId = "oXmQ_1ddd8Yq4C_oAhq_OiMG181c";
		UserInfoTest userInfoTest =  new UserInfoTest();
		UserInfoVo  userInfoVo =userInfoTest .getUserInfo(openId, accessToken);
		userInfoTest.getHeaderImg(userInfoVo);
		
	}

}
