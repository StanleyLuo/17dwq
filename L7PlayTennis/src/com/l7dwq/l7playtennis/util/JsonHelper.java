package com.l7dwq.l7playtennis.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {

    public static <T> T fromJson(String json, Class<T> clazz){
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }
    
    public static <T> List<T> fromJson(String json, TypeToken<List<T>> token){
        Gson gson = new Gson();
        Type t = token.getType();
        return gson.fromJson(json, t);
    }    
    
    public static <T> String toJson(T instance){
    	Gson gson = new Gson();
    	return gson.toJson(instance);
    }
}
