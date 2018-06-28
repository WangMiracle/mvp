package com.miracle.basemvp.base.delegate;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

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
    void setDaggerComponent();
}
