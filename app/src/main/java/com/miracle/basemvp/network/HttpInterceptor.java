package com.miracle.basemvp.network;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;


/**
 * 拦截器
 */
public class HttpInterceptor {
    public static final String TAG = "HttpInterceptor";

    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(message -> Log.e(TAG, "log: " + message)).setLevel(HttpLoggingInterceptor.Level.BASIC);//设置打印数据的级别
    }
}
