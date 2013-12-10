package com.l7dwq.l7playtennis.contract;

import java.io.Serializable;
import java.util.Date;

public class L7TennisCourt implements Serializable{

    public final static String COURT_PIC_URL_PREFIX = "http://www.17dwq.com/Public/arena/s_";
    
    
    public final static int COURT_SERVICE_SHOPPING = 1;
    public final static int COURT_SERVICE_WC = COURT_SERVICE_SHOPPING << 1;
    public final static int COURT_SERVICE_PARKING = COURT_SERVICE_WC << 1;
    public final static int COURT_SERVICE_TEACH = COURT_SERVICE_PARKING << 1;
    
    public int id;
    public int uid;
    public String name;
    public String address;
    public String phone;
    public String contacter;
    
    public int price;
    
    /**
     * 场地服务：如洗手间，更衣室，停车场
     */
    public int service;
    
    /**
     * 营业时间
     */
    public String businessHours;
    
    public String picture;
    
    public Date createTime;
    
    public String province;
    
    public String city;
    
    public String district;
    
}
