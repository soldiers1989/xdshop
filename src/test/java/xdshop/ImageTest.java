package xdshop;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import com.xdshop.vo.SceneVo;
import com.xdshop.vo.UserInfoVo;

public class ImageTest {

	public BufferedImage createContentImage(String content,int width,int height,int fontSize) throws IOException{
//		int width = 800;
//		int height =1000;
//		String content = "新华社北京8月28日电2018年“一带一路”知识产权高级别会议28日在北京开幕，国家主席习近平向会议致贺信。习近平指出，中国发扬丝路精神，提出共建“一带一路”倡议，得到有关国家和国际社会广泛认同和热情参与，取得了丰硕成果。我们愿同各方继续共同努力，本着共商共建共享原则，将“一带一路”建设成为和平之路、繁荣之路、开放之路、创新之路、文明之路，让丝路精神发扬光大。习近平强调，知识产权制度对促进共建“一带一路”具有重要作用。中国坚定不移实行严格的知识产权保护，依法保护所有企业知识产权，营造良好营商环境和创新环境。希望与会各方加强对话，扩大合作，实现互利共赢，推动更加有效地保护和使用知识产权，共同建设创新之路，更好造福各国人民。";
		//总字节数
		int totalByte = new ContentTest().getTotalByte(content);
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
		contentTableList = new ContentTest().getTableList(content, 0, byteNumOneRow, contentTableList);
		
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
	
	public static void main(String[] args) throws Exception {
		String accessToken = "13_aKf-EluwFt45xWS8tKK7IRuiqzSr0faoWj7vC-yio3px-wqftZczdLeDL8G-3SXxuA-AeuFOp9z2gGcSWsTGWgDeMPRaE1tq9iiIOlu9_f40jisPytqEf4hzaQqyBVVTna-QIYPetLtwo-YeVIMjACAHUY";
		String openId = "oXmQ_1ddd8Yq4C_oAhq_OiMG181c";
		String vopenId = "vopenid01";//首次生成分享图片,vopenId=vopenid01 如果是客户扫描转发的二维码海报，则vopenId = openId
		BufferedImage bgImage = ImageIO.read(new File("G:\\xdshop\\微信素材\\分享图背景.jpg"));
		//背景宽
		int bgWith = bgImage.getWidth();
		//背景高
		int bgHeight = bgImage.getHeight();
		System.out.println(bgWith+"|"+bgHeight);
		
		//获取公众号二维码
//		BufferedImage qrImage = ImageIO.read(new File("G:\\xdshop\\AppID相关\\二维码-测试公众号-接口获取.jpg"));
		SceneVo sceneVo = new SceneVo();
		sceneVo.setOpenId(vopenId);
		BufferedImage qrImage = ImageIO.read(new QrCodeTest().getQrPic(sceneVo,accessToken));
		Graphics2D g2d = bgImage.createGraphics();
		System.out.println(bgImage.getHeight()+"|"+bgImage.getWidth());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f));
		
		//写入：二维码
		int qrImageWidth = qrImage.getWidth()/2 + 50;
		int qrImageHeight = qrImage.getHeight()/2 + 50;
		System.out.println("bgWith|qrImageWidth:"+bgWith+"|"+qrImageWidth);
		g2d.drawImage(qrImage,  bgWith - qrImageWidth, bgHeight - qrImageHeight-60,qrImageWidth ,qrImageHeight ,null);
		
		//获取用户数据：头像
		UserInfoTest userInfoTest = new UserInfoTest();
		UserInfoVo userInfoVo = userInfoTest.getUserInfo(openId, accessToken);
		BufferedImage headerImage = ImageIO.read(userInfoTest.getHeaderImg(userInfoVo));
		int headerImgWith = headerImage.getWidth()/2;
		int headerImgHeight = headerImage.getHeight()/2;
		System.out.println("头像宽|高:"+headerImgWith+"|"+headerImgHeight);
		g2d.drawImage(headerImage, 20, bgHeight - qrImageHeight - 120, headerImgWith, headerImgHeight, null);
		
		//写入：别名
		String nickName = userInfoVo.getNickname();
		BufferedImage nickNameImage = new ImageTest().createContentImage(nickName, 400, 40, 20);
		int nickNameImageWidth =nickNameImage.getWidth();
		int nickNameHeight =nickNameImage.getHeight();
		g2d.drawImage(nickNameImage,headerImgWith +30 , bgHeight - qrImageHeight - 100, nickNameImageWidth, nickNameHeight,null);
		
		//写入：长按二维码进入领取
		String pressTips = "长按二维码进入领取";
		BufferedImage pressImage = new ImageTest().createContentImage(pressTips, 400, 40, 20);
		int pressImageWidth =pressImage.getWidth();
		int pressImageHeight =pressImage.getHeight();
		g2d.drawImage(pressImage,bgWith - qrImageWidth + 20 , bgHeight - qrImageHeight - 100, pressImageWidth, pressImageHeight,null);
		
		String operTips = "把海报分享到朋友圈和微信群可以更快获得免费门票哦";
		BufferedImage operTipsImage = new ImageTest().createContentImage(operTips, 600, 100, 40);
		int operTipsImageWidth =operTipsImage.getWidth();
		int operTipsImageHeight =operTipsImage.getHeight();
		g2d.drawImage(operTipsImage,20 , bgHeight - qrImageHeight - 50, operTipsImageWidth, operTipsImageHeight,null);
		
		String joinMe = "快来和我一起领取吧￥0";
		BufferedImage joinMeImage = new ImageTest().createContentImage(joinMe, 600, 60, 40);
		int joinMeImageWidth =joinMeImage.getWidth();
		int joinMeImageHeight =joinMeImage.getHeight();
		g2d.drawImage(joinMeImage,20 , bgHeight - qrImageHeight + 100, joinMeImageWidth, joinMeImageHeight,null);
		
		
		g2d.dispose();
			
		//输出合成图片
		ImageIO.write(bgImage, "jpg", new File("d:\\背景合成二维码-xiaoshua.jpg"));
		System.out.println("执行完成");
		
	}

}
