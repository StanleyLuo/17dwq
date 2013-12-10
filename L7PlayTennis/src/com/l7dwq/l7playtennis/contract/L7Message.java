package com.l7dwq.l7playtennis.contract;

import java.util.Date;


public class L7Message {

    public int id;
    public int fromUid;
    public int toUid;
    public String fromUserName;
    public String toUserName;
    public String title;
    public String content;
    public Date sendTime;
    public boolean isArrived;
    public boolean isRead;
}
