package com.l7dwq.l7playtennis.util;

import com.l7dwq.l7playtennis.L7Application;
import com.l7dwq.l7playtennis.contract.CountryInfo;
import com.l7dwq.l7playtennis.contract.CountryInfo.ProvinceInfo.CityInfo;
import com.l7dwq.l7playtennis.misc.ServerUrls;
import com.stanley.core.util.NetworkHelper;
import com.stanley.core.util.StringEx;

public class LocationHelper {

	public final static String ALL_CITY_JSON_FILE = "ChinaProvince_City_District_Streets.json";
	
	 /**
     * 获取指定城市的区域，街道信息
     * @return
     */
    public static CityInfo getCityInfo(String name){
		String jsonUrl = L7Application.serverUrl + "city/" + name + ".json";
		CityInfo result = null;
		String json = NetworkHelper.requestTextWithHttpGet(jsonUrl);
		if(!StringEx.isNullOrEmpty(json)){
			result = JsonHelper.fromJson(json, CityInfo.class);
		}
		
		return result;
	}
	
	/**
	 * 获取全国的省份，城市，区域，街道信息
	 * @return
	 */
    public static CountryInfo getCountryInfo() {
        String jsonUrl = L7Application.serverUrl + "city/"+ ALL_CITY_JSON_FILE;
        CountryInfo result = null;
        String json = NetworkHelper.requestTextWithHttpGet(jsonUrl);
        if (!StringEx.isNullOrEmpty(json)) {
            result = JsonHelper.fromJson(json, CountryInfo.class);
        }

        return result;
    }
}
