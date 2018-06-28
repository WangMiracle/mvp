package com.miracle.basemvp.base;


import com.miracle.basemvp.network.IRetrofitManager;

/**
 * @author Hexl
 * @desc
 * @date 2018/3/30 16:46
 * @modify
 * @modifyDate
 */
public class BaseModel implements IModel {

    protected IRetrofitManager mRetrofitManager;

    public BaseModel(IRetrofitManager retrofitManager) {
        this.mRetrofitManager = retrofitManager;
    }

    /**
     * 默认在BasePresenter中调用
     */
    @Override
    public void onDestroy() {
        if (mRetrofitManager != null) {
            mRetrofitManager = null;
        }
    }
}