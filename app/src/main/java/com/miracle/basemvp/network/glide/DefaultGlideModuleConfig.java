package com.miracle.basemvp.network.glide;


import android.content.Context;
import android.support.annotation.NonNull;


import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Priority;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.miracle.basemvp.di.component.AppComponent;
import com.miracle.basemvp.network.OkHttpUrlLoader;
import com.miracle.basemvp.util.Utils;

import java.io.InputStream;

/**
 * 更改Glide默认配置, 可以自定义缓存策略, 更换Glide默认网络请求
 */
@GlideModule
public class DefaultGlideModuleConfig extends AppGlideModule {
    /**
     * 可定制化项目的内存管理
     * 该方法提供了一个 builder 对象,可自定义方法
     * .setMemoryCache(MemoryCache memoryCache) //设置内存缓存
     * .setBitmapPool(BitmapPool bitmapPool)
     * .setDiskCache(DiskCache.Factory diskCacheFactory)//设置硬盘缓存
     * .setDiskCacheService(ExecutorService service)
     * .setResizeService(ExecutorService service)
     * .setDecodeFormat(DecodeFormat decodeFormat)//设置解码 默认Glide采用 RGB565
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //硬盘缓存大小为100mb
        int DEFAULT_DISK_CACHE_SIZE = 100 * 1024 * 1024;
        //内存缓存
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
//                .setArrayPoolSize()
//                .setBitmapPoolScreens()
//                .setMaxSizeMultiplier()
//                .setMemoryCacheScreens()
//                .build();

        builder
                .setMemorySizeCalculator(calculator)
                .setDiskCache(new InternalCacheDiskCacheFactory(context, "glide_cache", DEFAULT_DISK_CACHE_SIZE))
                .setDefaultRequestOptions(new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)//默认采用PREFER_RGB_565
                        .placeholder(android.R.mipmap.sym_def_app_icon)//加载中图片
                        .error(android.R.mipmap.sym_def_app_icon)//加载错误的图片
                        .priority(Priority.NORMAL)//设置初始缓存 HIGH 1.5倍 NORMAL 1倍 NORMAL 0.5倍,可动态设置,建议在加载GIF图时跳过掉内存缓存策略skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //默认Glide 网络请求使用 HttpUrlConnection 这里替换成 OkHttpClient
        AppComponent appComponent = Utils.obtainAppComponentFromContext(context);
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(appComponent.okHttpClient()));
    }

    /**
     * Glide v4 兼容 v3 所以默认会从manifest中读取 meta 标签, v4 已经支持注解方式进行声明,
     * {@link }已声明@GlideModule注解
     *
     * @return false 代表不读取manifest中标签 默认返回true
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

/*
    private RequestOptions getRequestOptions() {
        return new RequestOptions()
                .format(DecodeFormat.PREFER_ARGB_8888)//默认采用PREFER_RGB_565
                //.centerCrop()//图片显示类型
                .placeholder(android.R.mipmap.sym_def_app_icon)//加载中图片
                .error(android.R.mipmap.sym_def_app_icon)//加载错误的图片
                //.error(new ColorDrawable(Color.RED))//或者是个颜色值
                .priority(Priority.NORMAL)//设置初始缓存 HIGH 1.5倍 NORMAL 1倍 NORMAL 0.5倍,可动态设置,建议在加载GIF图时跳过掉内存缓存策略skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);//仅缓存原图片
        //.diskCacheStrategy(DiskCacheStrategy.ALL)//全部缓存
        //.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)缓存缩略过的
        //.onlyRetrieveFromCache(true)//仅从缓存加载
        //.skipMemoryCache(true)//禁用缓存,包括内存和磁盘
        //.diskCacheStrategy(DiskCacheStrategy.NONE)仅跳过磁盘缓存
        //.override(200,200)加载固定大小的图片
        //.dontTransform()不做渐入渐出的转换
        //.transition(new DrawableTransitionOptions().dontTransition())//同上
        //.circleCrop()设置成圆形头像<这个是V4.0新增的>
        //.transform(new RoundedCorners(10))设置成圆角头像<这个是V4.0新增的>
    }
*/
}
