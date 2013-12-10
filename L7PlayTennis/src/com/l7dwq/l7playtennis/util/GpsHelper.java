package com.l7dwq.l7playtennis.util;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class GpsHelper {

    private static final int REFRESH_LOCATION_INTERVAL = 5000;
    private static LocationClient mLocClient;

    public static void startGpsLocating(Context appContext, BDLocationListener resultListener) {
        LocationClientOption option = new LocationClientOption();
        option.setAddrType("all"); // // 返回的定位结果包含地址信息
        option.setOpenGps(true);
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setScanSpan(REFRESH_LOCATION_INTERVAL);
        option.disableCache(true);
        mLocClient = new LocationClient(appContext, option);
        mLocClient.registerLocationListener(resultListener);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mLocClient.requestLocation();

    }

    public static void stopGpsLocating() {
        if (mLocClient != null && mLocClient.isStarted()) {
            mLocClient.stop();
            mLocClient = null;
        }
    }
}
