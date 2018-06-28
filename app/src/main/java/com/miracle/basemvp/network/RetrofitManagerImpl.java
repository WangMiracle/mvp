package com.miracle.basemvp.network;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import retrofit2.Retrofit;

/**
 * com.miracle.basemvp.network
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 15:08
 * @see com.miracle.basemvp.network
 */
@Singleton
public class RetrofitManagerImpl implements IRetrofitManager {

    /**
     * 懒加载
     */
    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Application mApplication;

    @Inject
    public RetrofitManagerImpl() {
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> clazz) {
        return mRetrofit.get().create(clazz);
    }

    @Override
    public <T> T obtainCacheService(Class<T> clazz) {
        return null;
    }

    @Override
    public void clearAllCache() {

    }

    @Override
    public Context getContext() {
        return mApplication;
    }
}
