package xdshop;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
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
		String accessToken = "13_SW5WSU4eHHsh7QHtJblJzw5eOtNsXngqyEQSMDgzUXBLrFxoMEY84XKtufIuIt0mLjpmRyx1Q9S4OO-60D2fzrfobJkr-PnYUK_z2MC5kpedZOlTSpO5I7EUBOawd2P9i_g4HEG1ROEIsIOCEGFjAEAPRQ";
		String openId = "o_eRa0y5TyfJDlgOw_R6sp9_m_hk";
		
//		o_eRa0yrvq_trtqGRGukBwqfU8Qc   玲玲  
//		oXmQ_1ddd8Yq4C_oAhq_OiMG181c   我
		
		/**
		 * 重庆旅游生活号 openId
		 * 我：  o_eRa0_ilXHtq7bTo0RyRhC8LieA
		 * 玲玲：o_eRa0yrvq_trtqGRGukBwqfU8Qc
		 */
		
		UserInfoTest userInfoTest =  new UserInfoTest();
		UserInfoVo  userInfoVo =userInfoTest .getUserInfo(openId, accessToken);
		userInfoTest.getHeaderImg(userInfoVo);
		
	}

}
