package com.l7dwq.l7playtennis.contract;

import java.io.Serializable;

import com.stanley.core.util.StringEx;

public class L7UserInfo implements Serializable{

	public int uid;
	public String userName;
	public String email;
	public String mobile;
	//public String password;
	public int age;
	public String gender;
	//public Date birthday;
	public int skillLevel;
	
	/**
	 * 用于表示用户注册来源是否为第三方：如腾讯QQ，新浪微博. 0表示为本站注册用户
	 */
	public int from3rdParty;
	/**
	 * 头像Url
	 */
	public String avatarUrl = "default.jpg";
	public int lastip;
	public boolean state;
	public String bestDays = StringEx.Empty;
	public String bestTimes = StringEx.Empty;
	public String gameStyle = StringEx.Empty;
	public String singlesDoubles = StringEx.Empty;
	public String expiration = StringEx.Empty;
	public String expirationCheck = StringEx.Empty;
	public String profileInfo = StringEx.Empty;
	public String trueName = StringEx.Empty;
	public String province = StringEx.Empty;
	public String city = StringEx.Empty;
	public String district = StringEx.Empty;
	
	public String getLocation(){
		return String.format("%s-%s-%s", province, city, district);
	}
}
