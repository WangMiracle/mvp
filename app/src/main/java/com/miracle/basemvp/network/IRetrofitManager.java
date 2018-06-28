package com.miracle.basemvp.network;

import android.content.Context;

/**
 * com.miracle.basemvp.network
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 15:07
 * @see com.miracle.basemvp.network
 */
public interface IRetrofitManager {
    /**
     * 根据传入的clazz 提供相应Service
     *
     * @param clazz Api
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> clazz);

    /**
     * 根据传入的clazz 提供相应Service (有缓存)
     *
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> clazz);

    void clearAllCache();

    Context getContext();
}
