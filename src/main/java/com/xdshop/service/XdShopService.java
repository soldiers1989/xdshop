package com.xdshop.service;

public interface XdShopService {
	public boolean verifySinature(String signature,long timestamp,String nonce) throws Exception;
}
