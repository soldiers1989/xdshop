package com.xdshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xdshop.service.XdShopService;
import com.xdshop.util.Sha1Util;

@Service
public class XdShopServiceImpl implements XdShopService {
	private static final Logger logger = Logger.getLogger(XdShopServiceImpl.class);
	@Override
	public boolean verifySinature(String signature,long timestamp,String nonce) throws Exception {
		logger.info("收到signature："+signature);
		boolean isValid = false;
		/*Map<String,Object> decryptMap = new HashMap<String,Object>();
		decryptMap.put("token", "xdshop");
		decryptMap.put("timestamp", timestamp);
		decryptMap.put("nonce", nonce);*/
		List<String> decryptList = new ArrayList<String>();
		decryptList.add("xdshop");
		decryptList.add(timestamp+"");
		decryptList.add(nonce+"");
		String decrypt = Sha1Util.sha1Encryt(decryptList);
		logger.info("计算signature："+decrypt);
		if(signature.equals(decrypt)) {
			logger.info("验证签名：通过");
			isValid = true;
		}else {
			logger.info("验证签名：未通过");
			isValid = false;
		}
		return isValid;
	}

}
