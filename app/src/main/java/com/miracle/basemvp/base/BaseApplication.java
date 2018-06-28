package com.miracle.basemvp.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


import com.miracle.basemvp.App;
import com.miracle.basemvp.base.delegate.AppActivityCallbacksImpl;
import com.miracle.basemvp.di.component.AppComponent;
import com.miracle.basemvp.di.component.DaggerAppComponent;
import com.miracle.basemvp.di.module.AppModule;

import javax.inject.Singleton;

/**
 * @desc 全局Application {@link BaseApplication} 其他所有模块化的组件都要集成{@link BaseApplication}
 */
public class BaseApplication extends MultiDexApplication implements App {
    public static boolean DEBUG = true;
    protected AppComponent mAppComponent;
    protected AppActivityCallbacksImpl mAppActivityCallbacks;
    private static Context sContext;

    @Singleton
    public static Context getContext() {
        return sContext;
    }

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .build();
        mAppComponent.inject(this);

        //注册 ActivityLifecycle 回调对生命周期进行统一管理
        mAppActivityCallbacks = new AppActivityCallbacksImpl();
        this.registerActivityLifecycleCallbacks(mAppActivityCallbacks);
    }

    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}