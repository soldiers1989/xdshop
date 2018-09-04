package com.xdshop.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;


public class ImageUtils {
	public static BufferedImage createContentImage(String content,int width,int height,int fontSize) throws IOException{
//		int width = 800;
//		int height =1000;
//		String content = "新华社北京8月28日电2018年“一带一路”知识产权高级别会议28日在北京开幕，国家主席习近平向会议致贺信。习近平指出，中国发扬丝路精神，提出共建“一带一路”倡议，得到有关国家和国际社会广泛认同和热情参与，取得了丰硕成果。我们愿同各方继续共同努力，本着共商共建共享原则，将“一带一路”建设成为和平之路、繁荣之路、开放之路、创新之路、文明之路，让丝路精神发扬光大。习近平强调，知识产权制度对促进共建“一带一路”具有重要作用。中国坚定不移实行严格的知识产权保护，依法保护所有企业知识产权，营造良好营商环境和创新环境。希望与会各方加强对话，扩大合作，实现互利共赢，推动更加有效地保护和使用知识产权，共同建设创新之路，更好造福各国人民。";
		//总字节数
		int totalByte = ContentUtils.getTotalByte(content);
		System.out.println("总字节数："+totalByte);
		
		BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//		Graphics2D g2d = (Graphics2D) bufImage.getGraphics();
		Graphics2D g2d =  bufImage.createGraphics();
		
		bufImage = g2d.getDeviceConfiguration().createCompatibleImage(bufImage.getWidth(), bufImage.getHeight(),Transparency.TRANSLUCENT);
		g2d = bufImage.createGraphics();
		g2d.setBackground(Color.black);
		//打开有背景色，注释后，透明背景
//		g2d.clearRect(0, 0, width, height);
		
		g2d.setPaint(Color.red);
		
		Font font = new Font("Serif", Font.BOLD, fontSize);
		g2d.setFont(font);
		//抗锯齿
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		FontRenderContext frc = g2d.getFontRenderContext();
		//方法一：
		Rectangle2D stringBounds = font.getStringBounds(content, frc);
		//方法二：
		/*TextLayout textLayout = new TextLayout("This is a string",font,frc);
		Rectangle2D stringBounds = textLayout.getBounds();
		textLayout.draw(g2d, 0,(int) -stringBounds.getY());*/
//		System.out.println(stringBounds.getWidth()+"|"+stringBounds.getHeight()+"|"+stringBounds.getX()+"|"+stringBounds.getY());
		//水平居中
		//double x = (width - stringBounds.getWidth())/2;
		//靠左
		double x = 0;
		//y坐标为-stringBounds.getY()时，刚好显示在左上角
		double desend =-stringBounds.getY();
		//垂直居中
//		double y = desend + (height - stringBounds.getHeight())/2;
		//垂直靠上
		double y = desend;
		 
		//获取一个字节占用宽度
		Rectangle2D calOneByteSize = font.getStringBounds("1", frc);
		double oneByteWidth = calOneByteSize.getWidth();
		
		System.out.println("文本宽："+stringBounds.getWidth());
		System.out.println("总字节数："+totalByte);
		System.out.println("每个字节占用宽度"+stringBounds.getWidth()/totalByte);
		System.out.println("一个字节宽度："+oneByteWidth);
		System.out.println("行宽："+width);
		//每行字节数
		int byteNumOneRow = (int) (width/(stringBounds.getWidth()/totalByte));
		System.out.println("每行字节数："+byteNumOneRow);
		//切分content为表格
		LinkedList<String> contentTableList = new LinkedList<String>();
		contentTableList = ContentUtils.getTableList(content, 0, byteNumOneRow, contentTableList);
		
		int yIndex = 0;
		for (int i = 0; i < contentTableList.size(); i++) {
			String tempContent = contentTableList.get(i);
			g2d.drawString(tempContent, (int) x, (int) y + yIndex);
			yIndex = yIndex + 40;
		}
		
//		g2d.drawString(content, (int) x, (int) y);
//		g2d.drawString("重庆市89084322永川区大安街道", (int)x, (int)y+40);
		ImageIO.write(bufImage, "png", new File("d:\\content.png"));
		return bufImage;
	}
}
