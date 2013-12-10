package com.l7dwq.l7playtennis.misc;

import com.l7dwq.l7playtennis.L7Application;

public class ServerUrls {

    public final static String HOST_NAME_TEST = "192.168.1.100";
    public final static String HOST_NAME = "wxy001906.chinaw3.com";
    public final static int PORT_TEST = 8080;
    public final static int PORT = 8088;

    public final static String SERVER_URL = "http://" + HOST_NAME + ":" + PORT + "/17tennis/";
    
    public final static String SERVER_URL_TEST = "http://" + HOST_NAME_TEST + ":" + PORT_TEST + "/17tennis/";
    
    public static String getServerUrl4Upgrade(){
        return L7Application.serverUrl + "upgrade";
    }

    public static String getServerUrl4User(){
        return L7Application.serverUrl + "user";
    }
    
    public static String getServerUrl4Court(){
        return L7Application.serverUrl + "court";
    }
    
    public static String getServerUrl4Coach(){
        return L7Application.serverUrl + "coach";
    }
}
