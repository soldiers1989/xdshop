package xdshop;

import java.awt.MenuBar;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xdshop.util.HttpsUtil;
import com.xdshop.util.OkHttpUtil;
import com.xdshop.vo.MenuBtn;

import ch.qos.logback.classic.Logger;

public class MenuTest {

	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/menu/create";
		
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("access_token", "13_4qjmdoBUpjt41wR3Hpk7wjtrgQZS31oGRt74RFsYEEejleoScLjLAxOwgKtivTO20K4nkP5NvCibDp_Bk5TWnFieQwATNh5xHuNAf5d-3IvoPZ-LcXMw8UgDj5p6hz47UISDA18_Oia5VqruOYRcABAYIB");
		Map<String,Object> bodyParams = new HashMap<String,Object>();
		MenuBtn menuBtn = new MenuBtn();
		menuBtn.setKey("btnKey-baidu");
		menuBtn.setName("百度");
		menuBtn.setType("view");
		menuBtn.setUrl("https://www.baidu.com/");
		
		MenuBtn menuBtn2 = new MenuBtn();
		menuBtn2.setKey("btnKey-360");
		menuBtn2.setName("360");
		menuBtn2.setType("view");
		menuBtn2.setUrl("https://hao.360.cn");
		
		MenuBtn menuBtn3 = new MenuBtn();
		menuBtn3.setKey("btnKey-");
		menuBtn3.setName("淘宝网");
		menuBtn3.setType("view");
		menuBtn3.setUrl("https://www.taobao.com/");
		
		JSONArray menuBtnArray = new JSONArray();
		menuBtnArray.add(menuBtn);
		menuBtnArray.add(menuBtn2);
		menuBtnArray.add(menuBtn3);

		bodyParams.put("button", menuBtnArray);
		
		//创建菜单
		HttpsUtil.doPostJson(baseUrl, urlParams, bodyParams);
		
		//删除菜单
//		HttpsUtil.doGet("https://api.weixin.qq.com/cgi-bin/menu/delete", urlParams);
	}

}
