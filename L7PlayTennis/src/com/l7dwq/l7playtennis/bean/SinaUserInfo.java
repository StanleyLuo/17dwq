package com.l7dwq.l7playtennis.bean;

import java.io.Serializable;

/**
 * 
 * @author stanley
 *
 */
public class SinaUserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2890441034400789453L;
/**
 * 
 * {
	"id": 1904178193,
	"idstr": "1904178193",
	"screen_name": "微博开放平台",
	"name": "微博开放平台",
	"province": "11",
	"city": "8",
	"location": "北京 海淀区",
	"description": "#平台沙龙两周年#每期沙龙都离不开热爱平台的朋友们，您是否记得2010年10月初次相聚，我们一起见证平台启程；两年间，平台与开发者一同发...",
	"url": "",
	"profile_image_url": "http://tp2.sinaimg.cn/1904178193/50/5610154048/0",
	"profile_url": "openapi",
	"domain": "openapi",
	"weihao": "",
	"gender": "f",
	"followers_count": 62722,
	"friends_count": 48,
	"statuses_count": 1049,
	"favourites_count": 2,
	"created_at": "Mon Dec 27 17:56:46 +0800 2010",
	"following": false,
	"allow_all_act_msg": false,
	"geo_enabled": true,
	"verified": true,
	"verified_type": 2,
	"remark": "",
	"status": {
		"created_at": "Sun Feb 10 00:05:51 +0800 2013",
		"id": 3543945667338820,
		"mid": "3543945667338820",
		"idstr": "3543945667338820",
		"text": "新年快乐〜[爱你][爱你][爱你]",
		"source": "<a href=\"http://app.weibo.com/t/feed/3gwz8C\" rel=\"nofollow\">微格iPhone客户端</a>",
		"favorited": false,
		"truncated": false,
		"in_reply_to_status_id": "",
		"in_reply_to_user_id": "",
		"in_reply_to_screen_name": "",
		"geo": null,
		"reposts_count": 0,
		"comments_count": 0,
		"attitudes_count": 0,
		"mlevel": 0,
		"visible": {
			"type": 0,
			"list_id": 0
		}
	},
	"allow_all_comment": false,
	"avatar_large": "http://tp2.sinaimg.cn/1904178193/180/5610154048/0",
	"verified_reason": "新浪微博开放平台",
	"follow_me": false,
	"online_status": 0,
	"bi_followers_count": 40,
	"lang": "zh-cn",
	"star": 0,
	"mbtype": 0,
	"mbrank": 0,
	"block_word": 0
}
 */
	public long id;
	public String name;
	public String screen_name;
	public int province;
	public int city;
	public String location;
	public String profile_image_url;
	public String avatar_large;
	public boolean online_status;
}
