package xdshop;

import java.io.ByteArrayInputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import com.xdshop.vo.CDATAConvert;
import com.xdshop.vo.MsgRcvVo;

public class StreamTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		String xmlStr = "<xml><ToUserName><![CDATA[gh_999a217ce6b7]]></ToUserName><FromUserName><![CDATA[oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0]]></FromUserName><CreateTime>1534927754</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>6592464505569293178</MsgId></xml>";
		String xmlStr = "<xml><ToUserName><![CDATA[123]]></ToUserName><FromUserName>oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0</FromUserName><CreateTime>2222</CreateTime></xml>";
		byte[] byteArray = xmlStr.getBytes();
		
		xmlStr = xmlStr.replace("<![CDATA[", "");
		xmlStr = xmlStr.replace("]]>", "");
		System.out.println(xmlStr);
		ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
//		XStream xstream = new XStream();
		XStream xstream = new XStream(new StaxDriver());
		
		CDATAConvert convert = new CDATAConvert();
		xstream.registerConverter(convert);
		
		xstream.allowTypes(new Class[] {MsgRcvVo.class});
		xstream.alias("xml", MsgRcvVo.class);
		/*ObjectInputStream ois = xstream.createObjectInputStream(bais);
		Object msgRcvVo = ois.readObject();
//		MsgRcvVo msgRcvVo = (MsgRcvVo) xstream.fromXML(xmlStr, MsgRcvVo.class);
		System.out.println(msgRcvVo);*/
		MsgRcvVo msgRcvVo = (MsgRcvVo) xstream.fromXML(xmlStr);
		System.out.println(msgRcvVo);																	
		
		
		
		
		
		
		
	
	}

}
