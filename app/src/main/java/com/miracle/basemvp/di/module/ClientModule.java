package com.miracle.basemvp.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miracle.basemvp.base.BaseApplication;
import com.miracle.basemvp.network.NetWorkInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.miracle.basemvp.AppConfig.BASE_URL;

/**
 * com.miracle.basemvp.di.module
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 14:50
 * @see com.miracle.basemvp.di.module
 */
@Module
public class ClientModule {

    /**
     * 提供网络拦截器
     * @return
     */
    @Provides
    NetWorkInterceptor provideNetWorkInterceptor() {
        return new NetWorkInterceptor(BaseApplication.getContext());
    }

    /**
     * 提供RetrofitBuilder
     */
    @Singleton
    @Provides
    Retrofit.Builder providersRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    /**
     * 提供 GsonBuilder
     * @return
     */
    @Singleton
    @Provides
    com.google.gson.GsonBuilder providersGsonBuilder() {
        return new GsonBuilder();
    }

    /**
     * 提供Gson
     *
     * @param builder
     * @return 自定义一些Gson的配置
     */
    @Singleton
    @Provides
    Gson providersGson(GsonBuilder builder) {
        return builder
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
//                .setPrettyPrinting()
                .serializeNulls()//支持null值
                .enableComplexMapKeySerialization()//支持Object为key的map对象,默认只支持String为key的map
                .create();
    }


    /**
     * 提供OkHttpClient
     *
     * @param netWorkInterceptor 拦截器
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    OkHttpClient providersOkHttpClient(NetWorkInterceptor netWorkInterceptor) {
        int TIME_OUT = 10;
        return new OkHttpClient.Builder()
                //     .addInterceptor(HttpInterceptor.LogInterceptor())//Log日志拦截器
                .addNetworkInterceptor(netWorkInterceptor)//网络请求拦截器
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)//读操作超时时间
                .build();
    }

    /**
     * 提供Retrofit
     *
     * @param client
     * @param builder
     * @param gson
     * @return
     */
    @Singleton
    @Provides
    Retrofit providersRetrofit(OkHttpClient client, Retrofit.Builder builder, Gson gson) {

        return builder
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava2
                .addConverterFactory(GsonConverterFactory.create(gson))//添加Gson解析器
                .build();
    }
}
