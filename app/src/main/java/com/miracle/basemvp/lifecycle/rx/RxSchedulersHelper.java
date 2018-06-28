package com.miracle.basemvp.lifecycle.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @描述 线程切换
 * @创建者 何旭龙
 * @创建时间 2018/3/12 16:06
 * @修改者
 * @修改时间
 */
public class RxSchedulersHelper {

    public static <T> ObservableTransformer<T, T> io_main() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
