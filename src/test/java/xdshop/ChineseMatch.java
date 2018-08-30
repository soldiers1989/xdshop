package xdshop;

public class ChineseMatch {

	public static void main(String[] args) {
		String str = "“";
		for (int i = 0; i < str.length(); i++) {
			String tempStr = str.substring(i, i+1);
			boolean isMatch = tempStr.matches("[\u4e00-\u9fa5]+");
			System.out.println(tempStr+"|"+isMatch);
		}
		
	}

}
