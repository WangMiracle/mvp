package com.miracle.basemvp.util;

import android.app.Notification;
import android.content.Context;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.bumptech.glide.request.target.NotificationTarget;



public class GlideUtils {

    /**
     * 加载图片不使用缓存
     *
     * @param context 上下文
     * @param url     图片地址
     * @param view    图片控件
     */
    public static void loadSourseImgWithNoCache(@Nullable Context context, @Nullable String url, @Nullable ImageView view) {
        assert context != null;
        assert view != null;
//        GlideApp.with(context)
//                .load(url)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true)
//                .into(view);
    }

    /**
     * 加载一张gif 图片为静态图
     *
     * @param context 上下文
     * @param url     图片地址
     * @param view    图片控件
     */
    public static void loadGiftAsBitmap(@Nullable Context context,@Nullable  String url,@Nullable  ImageView view) {
        assert context != null;
        assert view != null;
//        GlideApp.with(context)
//                .asBitmap()
//                .load(url)
//                .into(view);
    }

    /**
     * 在通知栏中显示从网络上请求的图片
     *
     * @param context
     * @param remoteViews
     * @param viewId
     * @param notification
     * @param notificationId
     * @param url
     */
    public static void showImgInNotification(@Nullable Context context, RemoteViews remoteViews,@IdRes int viewId,
                                             Notification notification, int notificationId,@Nullable  String url) {
        assert context != null;
        NotificationTarget target = new NotificationTarget(context, viewId, remoteViews, notification, notificationId);
//        GlideApp.with(context.getApplicationContext())
//                .asBitmap()
//                .load(url)
//                .into(target);
    }

    /**
     * 显示本地视频(网络视频无效)
     *
     * @param context
     * @param filePath
     * @param imageView
     */
    public static void loadShowLocalVideo(@Nullable Context context,@Nullable  String filePath,@Nullable  ImageView imageView) {
        assert context != null;
        assert filePath != null;
        assert imageView != null;
//        GlideApp.with(context)
//                .load(Uri.fromFile(new File(filePath)))
//                .into(imageView);
    }

    /**
     * 清除内存缓存
     *
     * @param context 上下文
     */
    public static void clearMemoryCache(@Nullable Context context) {
        assert context != null;
//        GlideApp.get(context).clearMemory();
    }

    /**
     * 清除磁盘缓存
     *
     * @param context
     */
    public static void clearDiskCache(@Nullable Context context) {
        assert context != null;
//        GlideApp.get(context).clearDiskCache();
    }

    /**
     * 清除磁盘、内存 缓存
     *
     * @param context 上下文
     */
    public static void clearCache(@Nullable final Context context) {
        clearMemoryCache(context);
        new Thread(() -> clearDiskCache(context)).start();
    }
}
