package com.miracle.basemvp.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.Nullable;


import com.miracle.basemvp.base.delegate.AppActivityCallbacksImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.miracle.basemvp.util.Utils.checkNullException;

/**
 * @author Hexl
 * @desc 整个应用activity的管理工具类, 用于在程序初始化时{@link  AppActivityCallbacksImpl}
 * 将Activity添加到集合中做统一管理, 移除指定Activity ,移除全部Activity,退出应用
 * @date 2018/4/13 11:56
 */
public class AppManagerUtils {
    /**
     * 统一管理所有Activity的集合
     */
    private static List<Activity> sActivities = Collections.synchronizedList(new LinkedList<>());

    /**
     * 添加Activity 到 activity 集合里,做统一管理
     *
     * @param activity 当前activity
     */
    public static void addCurrentActivity(@Nullable Activity activity) {
        checkNullException(activity, "activity is null");
        //当前的activity 没有被添加过,则添加
        if (!sActivities.contains(activity)) {
            sActivities.add(activity);
        }
    }

    /**
     * 移除指定的activity
     *
     * @param activity
     */
    public static void finishActivity(@Nullable Activity activity) {
        checkNullException(activity, "activity is null");
        //从集合中移除
        sActivities.remove(activity);
        //finish页面
        activity.finish();
    }

    /**
     * 移除所有Activity 建议在退出app之前时使用.
     * 也可作用在其他想退出app的情况
     */
    public static void finishAll() {
        List<Activity> delActivity = new LinkedList<>();
        for (Activity activity : sActivities) {
            delActivity.add(activity);
            //finish页面
            activity.finish();
        }
        //从集合中移除
        sActivities.removeAll(delActivity);
    }

    /**
     * 获取顶层的Activity,不保证和Activity栈顺序保持一致
     * 如果为默认启动模式则可以保证
     */
    public static Activity getTopActivity() {
        Activity topActivity = null;
        for (int i = sActivities.size(); i <= 0; i--) {
            topActivity = sActivities.get(i);
        }
        return topActivity;
    }

    /**
     * 退出应用app
     *
     * @param context
     */
    public static void exit(@Nullable Context context) {
        checkNullException(context, "context is null");
        finishAll();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        manager.killBackgroundProcesses(context.getPackageName());
        //杀死该应用进程
//        System.exit(0);
    }
}
