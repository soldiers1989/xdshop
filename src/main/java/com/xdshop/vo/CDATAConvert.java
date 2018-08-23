package com.xdshop.vo;


import org.apache.log4j.Logger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class CDATAConvert implements Converter{
	private static final Logger logger = Logger.getLogger(CDATAConvert.class);
	@Override
	public boolean canConvert(Class arg0) {
		logger.info("canConvert");
		logger.info(arg0);
		return false;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter writer, MarshallingContext arg2) {
		logger.info("marshal");
		
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
		logger.info("unmarshal");
		return arg0.getValue();
	}

}
