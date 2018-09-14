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
 * 二维码测试
 * @author Administrator
 *
 */
public class QrCodeTest {

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
//		scene.put("scene_str", sceneVo.getOpenId());
		System.out.println(JSONObject.toJSONString(sceneVo));
		scene.put("scene_str", sceneVo.getOpenId()+"|"+sceneVo.getPublishId());
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
		String accessToken = "13_lK_DMHXVSoylh3QGE58as5P2yhV32ze36NB5HVz1iQRAJAkW6q3nRdq7sIoOhyD6Xh9_Vxo5OBC2xfhL_qRGp2vr77lKHzpLzEt6o9Siw_xhmEhYEGV5xjgeBYaLgel3iSn9a5-7RsHAOtlgIITjAIAMMX";
		QrCodeTest qrCodeTest = new QrCodeTest();
		SceneVo sceneVo = new SceneVo();
		sceneVo.setOpenId("oXmQ_1ddd8Yq4C_oAhq_OiMG181c");
		sceneVo.setPublishId("5555555555555");
		qrCodeTest.getQrPic(sceneVo,accessToken);
	}
}
