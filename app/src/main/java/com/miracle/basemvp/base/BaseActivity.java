package com.miracle.basemvp.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.miracle.basemvp.App;
import com.miracle.basemvp.base.delegate.IActivity;
import com.miracle.basemvp.di.component.AppComponent;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * com.miracle.basemvp.base
 * (c)2018 AIR Times Inc. All rights reserved.
 *
 *
 * @author WangJQ
 * @version 1.0
 * @date 2018/6/28 14:33
 * @see com.miracle.basemvp.base
 */
public abstract class BaseActivity<P extends IPresenter> extends RxAppCompatActivity implements
        IActivity, App {
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;

    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId(savedInstanceState));
        mContext = this;
        mUnbinder = ButterKnife.bind(this);
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        setDaggerComponent(getAppComponent());
        initView();
        initData();
        onRequest(providerEvent());
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    public void showActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public AppComponent getAppComponent() {
        return ((App) this.getApplicationContext()).getAppComponent();
    }

    @Override
    public LifecycleProvider<ActivityEvent> providerEvent() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
