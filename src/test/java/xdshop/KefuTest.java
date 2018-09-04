package xdshop;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.xdshop.util.HttpsUtil;
import com.xdshop.vo.QrTicketVo;
import com.xdshop.vo.SceneVo;

/**
 * 客服测试
 * @author Administrator
 *
 */
public class KefuTest {

	/**
	 * 获取ticket
	 * @return
	 */
	public QrTicketVo getTickt(SceneVo sceneVo,String accessToken){
		QrTicketVo qrTicketVo = null;
		String baseUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("access_token", accessToken);
		Map<String,Object> bodyParams = new HashMap<String,Object>();
//		bodyParams.put("expire_seconds", 2592000);
		bodyParams.put("action_name", "QR_LIMIT_STR_SCENE");
		
		JSONObject actionInfo = new JSONObject();
		JSONObject scene = new JSONObject();
		scene.put("scene_str", sceneVo.getOpenId());
//		scene.put("scene_id", 1234);
		actionInfo.put("scene", scene);
		bodyParams.put("action_info", actionInfo);
		String msgRcvStr = HttpsUtil.doPostJson(baseUrl, urlParams, bodyParams);
		
		qrTicketVo = JSONObject.parseObject(msgRcvStr, QrTicketVo.class); 
		return qrTicketVo;
	}
	
	/**
	 * 生成二维码图片
	 * @param qrTicketVo
	 * @throws IOException
	 */
	public InputStream getQrPic(SceneVo sceneVo,String accessToken) throws IOException{
		QrTicketVo qrTicketVo = this.getTickt(sceneVo,accessToken);
		
		System.out.println("ticket:"+qrTicketVo.getTicket());
		System.out.println("url:"+qrTicketVo.getUrl());
		
		String baseUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("ticket", qrTicketVo.getTicket());
		byte[] byteRcv = HttpsUtil.doGetByte(baseUrl, urlParams);
		System.out.println("接收字节长度："+byteRcv.length);
//		Base64 base64 = new Base64();
//		byte[] imageByte = base64.decode(byteRcv);
//		System.out.println("接收字节长度,解码后："+imageByte.length);
		File file = new File("d:\\qrImage.jpg");
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		
		fos.write(byteRcv, 0, byteRcv.length);
		fos.flush();
		fos.close();
		return new ByteArrayInputStream(byteRcv);
	}
	
	public static void main(String[] args) throws Exception {
		String accessToken = "13_ZTdyqKPf-Xfjg0HUmb88dUOrmi1k9udpzwb4YpZbdRxaIZB5ZFYj8QNqjJJ0RFjvoz5O0XhAgDa5tbgit-cHsxmYny75Qoqqx3fw6c9RDwiDfmbOb06TYxsqq7-SQnJNVLj6VKr21vHh5XjDJCAeAFABUS";
		String baseUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
		Map<String,Object> urlParams = new HashMap<String,Object>();
		urlParams.put("access_token", accessToken);
		
		Map<String,Object> bodyParams = new HashMap<String,Object>();
		bodyParams.put("kf_account", "test1@test");
		bodyParams.put("nickname", "客服0000");
		bodyParams.put("password", "123456");
		HttpsUtil.doPost(baseUrl, urlParams, bodyParams);
	}

}
