package com.miracle.basemvp.base.delegate;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.miracle.basemvp.di.component.AppComponent;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * com.miracle.basemvp.base.delegate
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 14:34
 * @see com.miracle.basemvp.base.delegate
 */
public interface IActivity {
    /**
     * 绑定布局
     * @param savedInstance
     * @return
     */
    @LayoutRes
    int setLayoutId(@Nullable Bundle savedInstance);

    /**
     * 初始化视图
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 注入Dagger
     */
    void setDaggerComponent(AppComponent appComponent);

    /**
     * 是否使用eventbus
     * @return
     */
    boolean useEventBus();

    /**
     * 默认在onCreate中调用,请求网络数据.如当前页面无需网络请求,可不实现此方法,
     */
    void onRequest(LifecycleProvider<ActivityEvent> eventLifecycleProvider);

    /**
     * 提供ActivityEvent 用于监听RxActivity的生命周期
     *
     * @return 当前activity的LifecycleProvider
     */
    LifecycleProvider<ActivityEvent> providerEvent();

}
