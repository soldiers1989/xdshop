package com.pujjr.test;
import com.alibaba.fastjson.JSONObject;
import com.tang.xmlopr.TransactionMapData;
import com.tang.xmlopr.XmlFormate;
import com.tang.xmlopr.XmlUnformate;

public class TestPack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestPack tp = new TestPack();
		XmlFormate xmlFormate = new XmlFormate();
		XmlUnformate xmlUnformate = new XmlUnformate();
//		TransactionMapData tmd = TransactionMapData.getInstance();
		TransactionMapData tmd = new TransactionMapData();
		
		String xmlRcvBuf = "";
		String xmlPackRes = "";
		
		
		/*tmd.put("tranCode", "f0001");
		tmd.put("mdCardNo", "mdCardNo");
		tmd.put("passWord", "");
		xmlPackRes = xmlFormate.xmlPack(tmd);//打包							// xml报文打包
		System.out.println(xmlPackRes);
		xmlRcvBuf = "<?xml version=\"1.0\" encoding=\"utf-8\"?><UTILITY_PAYMENT><CONST_HEAD><REQUEST_TYPE>01</REQUEST_TYPE><COUNT>烦都烦死</COUNT></CONST_HEAD><DATA_AREA><USER_CODE>200810405234</USER_CODE><DESC><REC><DATE>20140101</DATE><JE>100</JE><ZNJ>10</ZNJ></REC><REC><DATE>20140202</DATE><JE>200</JE><ZNJ>20</ZNJ></REC><REC><DATE>20140303</DATE><JE>300</JE><ZNJ>30</ZNJ></REC></DESC></DATA_AREA></UTILITY_PAYMENT>";
		xmlUnformate.xmlUnpack(tmd, xmlRcvBuf);//解包
		System.out.println(tmd.get("date"));
		*/
		
		tmd.put("tranCode", "f0002");
		tmd.put("node1", "node1内容");
		tmd.put("node2", "node2内容");
		xmlPackRes = xmlFormate.xmlPack(tmd);//打包							// xml报文打包
		System.out.println("xml打包结果："+xmlPackRes);
		xmlRcvBuf = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root><maccount><![CDATA[账户2222<890>[]iotr-==-=<>]]></maccount><maccountname>户名</maccountname><xmbh2>项目数字编号</xmbh2><conman>管户客户经理姓名</conman><contel>管户客户经理电话</contel></root>";
		xmlUnformate.xmlUnpack(tmd, xmlRcvBuf);//解包
		System.out.println(tmd.get("maccount"));
		
		/**
		 * 微信上送报文解包
		 * <xml><ToUserName><![CDATA[gh_999a217ce6b7]]></ToUserName><FromUserName><![CDATA[oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0]]></FromUserName><CreateTime>1534927754</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>6592464505569293178</MsgId></xml>
		 */
		String wechatIn = "<xml><ToUserName><![CDATA[gh_999a217ce6b7]]></ToUserName><FromUserName><![CDATA[oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0]]></FromUserName><CreateTime>1534927754</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>6592464505569293178</MsgId></xml>";
		tmd.put("tranCode", "f0003");
		xmlUnformate.xmlUnpack(tmd, wechatIn);//解包
		
	}
}
