package com.miracle.basemvp.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.miracle.basemvp.base.BaseActivity;
import com.miracle.basemvp.di.module.AppModule;
import com.miracle.basemvp.di.module.ClientModule;
import com.miracle.basemvp.network.IRetrofitManager;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * com.miracle.basemvp.di.component
 * (c)2018 AIR Times Inc. All rights reserved.
 * 全局app
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 15:05
 * @see com.miracle.basemvp.di.component
 */
@Singleton
@Component(modules = {AppModule.class, ClientModule.class})
public interface AppComponent {

    Application application();

    IRetrofitManager retrofitManager();

    OkHttpClient okHttpClient();

    Retrofit retrofit();

    Gson gson();

    void inject(Application application);
}
