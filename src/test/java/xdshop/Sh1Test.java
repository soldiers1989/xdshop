package xdshop;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		String str = "4955614371534869436xdShop";
		msgDigest.update(str.getBytes());
		String str3 = Sh1Test.getFormattedText(msgDigest.digest());
//		String str3 = new String(msgDigest.digest(str.getBytes()));
		System.out.println(str3);
		String str2 = new String(Base64Utils.encode(msgDigest.digest(str.getBytes())));
		System.out.println(str2);
		
		Map map = new HashMap<String,String>();
		//模拟收到的签名
		String signature = "cee2dd619d41df99c76d801ab19d99bfae467e60";
		//模拟收到的参数组成的消息摘要数组
		map.put("token", "xdshop");
		map.put("timestamp", "1534869138");
		map.put("nonce", "1725671455");
		
		List<String> list = new ArrayList<String>();
		list.add("xdshop");
		list.add("1534869138");
		list.add("1725671455");
/*		Collections.sort(list);
		StringBuilder sb = new StringBuilder();
		for (String string : list) {
			sb.append(string);
		}
		System.out.println(sb.toString());*/
		
//		System.out.println("调用Sha1，解密后:"+new Sha1Util().sha1Encryt(map));
		System.out.println("验签结果："+Sha1Util.verifySinature(signature, list));
		
		
	}
}
