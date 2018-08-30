package xdshop;

import java.util.LinkedList;

public class ContentTest {
	String chnStrReg = "[\u4e00-\u9fa5]+|，|“|”|。";
	public int getTotalByte(String content){
		int totalByte = 0;
		for (int i = 0; i < content.length(); i++) {
			String tempStr = content.substring(i, i+1);
			boolean isMatch = tempStr.matches(chnStrReg);
			System.out.println(tempStr+"|"+isMatch);
			if(isMatch)
				totalByte = totalByte + 2;
			else 
				totalByte = totalByte + 1;
		}
		return totalByte;
	}

	/**
	 * 获取当前行字符
	 * @param content 待切分字符串
	 * @param currIndex 当前指针索引
	 * @param byteNumOneRow 一行的字节数
	 * @param tableList 切分后表格
	 * @return
	 */
	public int getCurrRow(String content,int currIndex,int byteNumOneRow,LinkedList<String> tableList){
		StringBuffer rowSb = new StringBuffer();
		//当前行字节
		int currRowByte = 0;
		int nextIndex = 0;
		for (int i = currIndex; i <content.length(); i++) {
			String currChar = content.substring(i, i+1);
			int currCharByte = 0;
			boolean isMatch = currChar.matches(chnStrReg);
			if(isMatch)
				currCharByte =  2;
			else 
				currCharByte =  1;
			currRowByte = currRowByte + currCharByte;
			if(currRowByte <= byteNumOneRow){
				rowSb.append(currChar);
				nextIndex = i;
				continue;
			}else{
				nextIndex = i;
				break;
			}	
		}
		tableList.add(rowSb.toString());
		System.out.println(currIndex+"|"+nextIndex+",当前行："+rowSb.toString());
		return nextIndex;
	}
	
	public LinkedList<String> getTableList(String content,int currIndex,int byteNumOneRow,LinkedList<String> tableList){
		currIndex = this.getCurrRow(content,currIndex, byteNumOneRow,tableList);
		while(currIndex < content.length() - 2){
			currIndex = this.getCurrRow(content,currIndex, byteNumOneRow,tableList);
		}
		return tableList;
	}
	
	public static void main(String[] args) {
		String content = "“。，，。，，，，，。";
		System.out.println(content.length());
		LinkedList<String> tableList = new LinkedList<String>();
		//一行字节数
		int byteNumOneRow = 90;
		int currIndex = 0;
		ContentTest contentTest = new ContentTest();
		System.out.println("totalByte:"+contentTest.getTotalByte(content));
		tableList = contentTest.getTableList(content, currIndex, byteNumOneRow, tableList);
		System.out.println(tableList);
	}
}
