package com.example.lenovo.textbeijing.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.lenovo.textbeijing.SplashActivity;
import com.example.lenovo.textbeijing.activity.GuideActivity;

//缓存软件的参数和数据
public class CacheUtils {

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("aaaaaaaa",Context.MODE_PRIVATE);



        return sp.getBoolean(key,false);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("aaaaaaaa",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();


    }
/*
* 缓存文本数据
* */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("aaaaaaaa",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();

    }
/*获取的文本信息
* */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("aaaaaaaa",Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
