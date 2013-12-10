package com.l7dwq.l7playtennis.contract;

import java.io.Serializable;

public class L7CoachInfo implements Serializable{

    public static final String COACH_PHOTO_URL_PREFIX = "http://www.17dwq.com/Public/coach/s_";
    
    public int id;
    public int uid;
    public String name;
    public String age;
    public int sex;
    public String phone;
    public String photo;
    public String content;
    public int addtime;
    public String province;
    public String city;
    public String district;
}
