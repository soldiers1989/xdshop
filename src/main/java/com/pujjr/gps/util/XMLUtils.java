package com.pujjr.gps.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
//import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xml 工具类
 * @author 180008
 *
 */
public final class XMLUtils {

	protected static Logger logger = LoggerFactory.getLogger(XMLUtils.class);

	/**
	 * 将对象转为xml数据格式
	 * @param classObject 需要翻译的类型对象
	 * @param bEReq 业务对象名称
	 * @return 返回xml格式数据
	 */
	public static final String jaxBeanToXml(Object classObject) {
		String xml = StringUtils.EMPTY;
		StringWriter writer = null;
		try {
			JAXBContext context = JAXBContext.newInstance(classObject.getClass());

			Marshaller marshaller = context.createMarshaller();
			// 编码格式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//			// 是否省略xml头信息
			//			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			writer = new StringWriter();
			marshaller.marshal(classObject, writer);
			xml = writer.toString().replace(" standalone=\"yes\"", "")
					.replace(" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"true\"", "");
			writer.flush();
		} catch (Exception e) {
			logger.error("对象转换xml格式异常", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("对象转换xml格式异常", e);
				}
			}
		}
		return xml;
	}

	/**
	 * 将对象转为xml数据格式
	 * @param classObject 需要翻译的类型对象
	 * @param classesToBeBound 需要翻译的类型包括的业务类
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static final String jaxBeanToXml(Object classObject,Class... classesToBeBound) {
		String xml = StringUtils.EMPTY;
		StringWriter writer = null;
		try {
			JAXBContext context = JAXBContext.newInstance(classesToBeBound);

			Marshaller marshaller = context.createMarshaller();
			// 编码格式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// 是否格式化生成的xml串
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 是否省略xml头信息
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

			writer = new StringWriter();
			marshaller.marshal(classObject, writer);
			xml = writer.toString();
			writer.flush();
		} catch (Exception e) {
			logger.error("对象转换xml格式异常", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error("对象转换xml格式异常", e);
				}
			}
		}
		return xml;
	}

	/**
	 * 将xml数据转换为对象
	 * @param xml xml 数据
	 * @param c 类型
	 * @return 返回对象
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T xmlToJaxBean(String xml, Class<T> c) {
		T classObject = null;
		StringReader sr = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			sr = new StringReader(xml);
			classObject = (T) unmarshaller.unmarshal(sr);
		} catch (Exception e) {
			logger.error("xml数据转换对象异常", e);
		} finally {
			if (sr != null) {
				sr.close();
			}
		}
		return classObject;
	}
	//	/**
	//	 * 将xml数据转换为对象
	//	 * @param xml xml 数据
	//	 * @param c 类型
	//	 * @return 返回对象
	//	 */
	//	@SuppressWarnings("unchecked")
	//	public static final <T> T NodeToJaxBean(Node node, Class<T> c) {
	//		T classObject = null;
	//		StringReader sr = null;
	//		try {
	//			JAXBContext context = JAXBContext.newInstance(c);
	//			Unmarshaller unmarshaller = context.createUnmarshaller();
	//			//sr = new StringReader(xml);
	//			classObject = (T) unmarshaller.unmarshal(node);
	//		} catch (Exception e) {
	//			logger.error("xml数据转换对象异常", e);
	//		} finally {
	//			if (sr != null) {
	//				sr.close();
	//			}
	//		}
	//		return classObject;
	//	}

	/*@SuppressWarnings({ "unchecked" })
	public static <T> List<T> readXML(String xml, Class<T> t) {   
		long lasting = System.currentTimeMillis();//效率检测   
		List<T> list = new ArrayList<T>();//创建list集合   
		try {   
			Document document = DocumentHelper.parseText(xml);
			Field[] properties = t.getDeclaredFields();//获得实例的属性   
			//实例的set方法   
			Method setmeth;
			List<Node> nodes=document.selectNodes("/response/CERT_LIST");
			for (Node node:nodes) {//遍历t.getClass().getSimpleName()节点   
				Element root = (Element) node;
				T obj=(T)t.newInstance();
				for (int j = 0; j < properties.length; j++) {//遍历所有孙子节点   
					//实例的set方法   
					try { 
						System.out.println("set"+ properties[j].getName()+"--"+properties[j].getType());
						System.out.println(properties[j].getName());
						setmeth = t.getMethod("set"+ properties[j].getName(),properties[j].getType());   
						setmeth.invoke(obj, root.elementText(properties[j].getName()));//将对应节点的值存入 
					}catch (Exception e) {   
						e.printStackTrace();   
					}   
				}   
				list.add(obj);   
			}   
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
		long lasting2 = System.currentTimeMillis();   
		System.out.println("读取XML文件结束,用时"+(lasting2 - lasting)+"ms");   
		return list;   
	}  */

	/**
	 * XML转对象
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T> T ccbXmlToJaxBean(String xml, Class<T> c){
		String rootNodeName = c.getAnnotation(XmlRootElement.class).name();
		xml = xml.substring(xml.indexOf("<"+rootNodeName), xml.lastIndexOf("</"+rootNodeName+">")+rootNodeName.length()+3);
		T classObject = null;
		StringReader sr = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			sr = new StringReader(xml);
			classObject = (T) unmarshaller.unmarshal(sr);
		} catch (Exception e) {
			logger.error("xml数据转换对象异常", e);
		} finally {
			if (sr != null) {
				sr.close();
			}
		}
		return classObject;
	}

	/**
	 * 获得银行返回的错误消息
	 * @param resp
	 * @return
	 */
	public static final String getErrorMessage(String resp){
		int startInt = resp.indexOf("<errorMessage>");
		int endInt = resp.indexOf("</errorMessage>");
		if (startInt== -1||endInt==-1) {
			return "数据错误";
		}
		return resp.substring(startInt + 14, endInt);
	}

	/**
	 * 获取错误码
	 * @param resp
	 * @return
	 */
	public static final String getErrorCode(String resp){
		int startInt = resp.indexOf("<errorCode>");
		int endInt = resp.indexOf("</errorCode>");
		if (startInt== -1||endInt==-1) {
			return "数据错误";
		}
		return resp.substring(startInt + 11, endInt);
	}

	/**
	 * 金额精确到分
	 * @param currentAmount
	 * @return
	 */
	public static final int getCurrencyFen(String currentAmount){
		if (StringUtils.isEmpty(currentAmount)) {
			return 0;
		} else {
			BigDecimal amountBD100=new BigDecimal(100);
			BigDecimal amountBD=new BigDecimal(currentAmount);
			amountBD=amountBD.multiply(amountBD100);//金额乘以100，由元到分
			return amountBD.intValue();//分
		}
	}

}
