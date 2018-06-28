package com.miracle.basemvp.base;

/**
 *
 */
public interface IPresenter {
    /**
     * 在BasePresenter 的构造函数默认调用 ,可以做些初始化操作
     */
    void onStart();

    /**
     * 当 V 层被 finish 后,判断 v 和 m 是否还持有引用没有被释放
     */
    void onDestroy();
}
