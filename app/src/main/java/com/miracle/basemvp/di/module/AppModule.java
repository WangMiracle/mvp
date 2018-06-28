package com.miracle.basemvp.di.module;

import android.app.Application;

import com.miracle.basemvp.network.IRetrofitManager;
import com.miracle.basemvp.network.RetrofitManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * com.miracle.basemvp.di.module
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 15:06
 * @see com.miracle.basemvp.di.module
 */
@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return mApplication;
    }

    /**
     * 提供RepositoryManager(方法二)
     * 需要在构造函数中加入@Inject 注解,
     *
     * @param retrofitManager
     * @return
     */
    @Provides
    IRetrofitManager providersRetrofitManager(RetrofitManagerImpl retrofitManager) {
        return retrofitManager;
    }
}
