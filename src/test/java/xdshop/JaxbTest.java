package xdshop;

import com.pujjr.gps.util.XMLUtils;
import com.xdshop.vo.MsgRcvVo;

public class JaxbTest {

	public static void main(String[] args) {
		//xml反序列化为对象
		String xmlStr = "<xml><ToUserName><test><![CDATA[gh_999a217ce6b7]]></test></ToUserName><FromUserName><![CDATA[oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0]]></FromUserName><CreateTime>1534927754</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>6592464505569293178</MsgId></xml>";
		MsgRcvVo msgRcvVo = XMLUtils.xmlToJaxBean(xmlStr, MsgRcvVo.class);
		System.out.println("userName:"+msgRcvVo.getToUserName());
		
		//对象序列化为xml
		MsgRcvVo msgRcvVoToMarsh = new MsgRcvVo();
		msgRcvVoToMarsh.setFromUserName("brighttang");
		System.out.println(XMLUtils.jaxBeanToXml(msgRcvVoToMarsh));

	}

}
