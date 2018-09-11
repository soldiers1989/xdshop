package com.xdshop.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 用户信息对象
 * 返回信息如下：
 * {"subscribe":1,"openid":"oXmQ_1ddd8Yq4C_oAhq_OiMG181c","nickname":"TOM","sex":1,"language":"zh_CN","city":"永川","province":"重庆","country":"中国","headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/OxUBpiaYgpHgn4toKEsf4GopJbhicoIMia6JIOB757L4Iv9wg1WuUEKhBf6icVzibr9m765wV6CVULianzSQic8mxp0qw\/132","subscribe_time":1535434157,"remark":"","groupid":0,"tagid_list":[],"subscribe_scene":"ADD_SCENE_QR_CODE","qr_scene":0,"qr_scene_str":"oXmQ_1ddd8Yq4C_oAhq_OiMG181c"}
 * @author Administrator
 *
 */
public class UserInfoVo {
	private int subscribe;
	private String openid;
	private String nickname;
	private int sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headimgurl;
	private long subscribe_time;
	private String remark;
	private Object[] tagid_list;
	private String subscribe_scene;
	private int qr_scene;
	private String qr_scene_str;
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		String tempNickName = "";
		try {
			tempNickName = URLDecoder.decode(nickname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return tempNickName;
	}
	public void setNickname(String nickname) {
		try {
			nickname = URLEncoder.encode(nickname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public long getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Object[] getTagid_list() {
		return tagid_list;
	}
	public void setTagid_list(Object[] tagid_list) {
		this.tagid_list = tagid_list;
	}
	public String getSubscribe_scene() {
		return subscribe_scene;
	}
	public void setSubscribe_scene(String subscribe_scene) {
		this.subscribe_scene = subscribe_scene;
	}
	public int getQr_scene() {
		return qr_scene;
	}
	public void setQr_scene(int qr_scene) {
		this.qr_scene = qr_scene;
	}
	public String getQr_scene_str() {
		return qr_scene_str;
	}
	public void setQr_scene_str(String qr_scene_str) {
		this.qr_scene_str = qr_scene_str;
	}
	
	
}
