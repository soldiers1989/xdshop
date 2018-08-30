package xdshop;

import java.util.ArrayList;
import java.util.List;

import com.xdshop.util.XMLUtils;
import com.xdshop.vo.ArticleVo;
import com.xdshop.vo.MsgRcvVo;
import com.xdshop.vo.MsgRetVo;

public class XmlTest {

	public static void main(String[] args) {
		String xmlStr = "<xml><ToUserName><![CDATA[gh_999a217ce6b7]]></ToUserName><FromUserName><![CDATA[oC5dH6M6LvA7OJ0v1NJ4mHT5Nqv0]]></FromUserName><CreateTime>1534927754</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[123]]></Content><MsgId>6592464505569293178</MsgId></xml>";
		MsgRcvVo msgRcvVo = XMLUtils.xmlToJaxBean(xmlStr, MsgRcvVo.class);
		System.out.println(msgRcvVo.getFromUserName());
		
		System.out.println(XMLUtils.jaxBeanToXml(msgRcvVo));
		
		MsgRetVo msgRetVo = new MsgRetVo();
		List<ArticleVo> articles = new ArrayList<ArticleVo>();
		ArticleVo articleVo = new ArticleVo();
		articleVo.setTitle("标题");
		articleVo.setDescription("描述");
		articleVo.setPicUrl("picUrl");
		articleVo.setUrl("url");
		articles.add(articleVo);
		articles.add(articleVo);
		articles.add(articleVo);
		articles.add(articleVo);
		msgRetVo.setArticles(articles);
		System.out.println(XMLUtils.jaxBeanToXml(msgRetVo));
		

	}

}
