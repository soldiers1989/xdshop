package xdshop;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import com.pujjr.gps.util.Sha1Util;

public class Sh1Test {
	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 
	 private static String getFormattedText(byte[] bytes) {  
	        int len = bytes.length;  
	        StringBuilder buf = new StringBuilder(len * 2);  
	        // 把密文转换成十六进制的字符串形式  
	        for (int j = 0; j < len; j++) {  
	            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);  
	            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);  
	        }  
	        return buf.toString();  
	    }  
	
	public static void main(String[] args) throws Exception {
		MessageDigest msgDigest = DigestUtils.getSha1Digest();
		System.out.println(msgDigest.getAlgorithm());
		String str = "xdShop15348287621356781577";
		msgDigest.update(str.getBytes());
		String str3 = Sh1Test.getFormattedText(msgDigest.digest());
//		String str3 = new String(msgDigest.digest(str.getBytes()));
		System.out.println(str3);
		String str2 = new String(Base64Utils.encode(msgDigest.digest(str.getBytes())));
		System.out.println(str2);
		
		Map map = new HashMap<String,String>();
		//模拟收到的签名
		String signature = "2c872b3168395101758d20c08a98e09b7dddb28f";
		//模拟收到的参数组成的消息摘要数组
		map.put("token", "xdShop");
		map.put("timestamp", "1534828762");
		map.put("nonce", "1356781577");
		
		System.out.println("调用Sha1，解密后:"+new Sha1Util().sha1Encryt(map));
		System.out.println("验签结果："+Sha1Util.verifySinature(signature, map));
		
		
	}
}
