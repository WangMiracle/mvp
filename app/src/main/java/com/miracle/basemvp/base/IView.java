package com.miracle.basemvp.base;

/**
 * @desc 所有view 接口 都继承此接口
 */
public interface IView<R> {
    void showData(R r);
}