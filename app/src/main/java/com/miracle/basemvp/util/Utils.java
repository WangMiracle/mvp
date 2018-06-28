package com.miracle.basemvp.util;

import android.content.Context;

import com.miracle.basemvp.App;
import com.miracle.basemvp.di.component.AppComponent;

/**
 * @author Hexl
 * @desc
 * @date 2018/4/2 11:42
 * @modify
 * @modifyDate
 */
public class Utils {
    /**
     * 检查空指针异常
     *
     * @param errorMsg 异常log
     */
    public static void checkNullException(Object obj, String errorMsg) {
        if (obj == null) {
            throw new NullPointerException(errorMsg);
        }
    }

    /**
     * 检查空指针异常
     */
    public static void checkNullException(Object object) {
        if (object == null) {
            throw new NullPointerException("Please check value" );
        }
    }

    public static AppComponent obtainAppComponentFromContext(Context context) {
        checkNullException(context,"context not null");
        return ((App) context).getAppComponent();
    }
}