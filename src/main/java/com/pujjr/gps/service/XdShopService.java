package com.pujjr.gps.service;

import java.util.Map;

public interface XdShopService {
	public void verifySinature(String signature,Map<String,String> decryptMap) throws Exception;
}
