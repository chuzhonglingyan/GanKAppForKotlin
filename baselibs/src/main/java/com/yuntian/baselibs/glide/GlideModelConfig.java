package com.yuntian.baselibs.glide;

import android.content.Context;

import com.blankj.utilcode.util.AppUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

import java.io.File;


/**
 * @author guangleilei
 * @version 1.0 2017-08-29
 */
public class GlideModelConfig implements GlideModule {

    int diskSize = 1024 * 1024 * 200;  //200mb


    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        String APP_IMAGE_CACHE= AppUtils.getAppName()+ File.separator+"imags";
        // 默认内存和图片池大小
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize(); // 默认内存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 默认图片池大小
        builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSize)); // 该两句无需设置，是默认的
        builder.setBitmapPool(new LruBitmapPool(defaultBitmapPoolSize));
        //定义图片的本地磁盘缓存
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, APP_IMAGE_CACHE, diskSize)); //外部app中
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, APP_IMAGE_CACHE, diskSize)); //外部app中

        // 定义图片格式
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565); // 默认
        // 自定义内存和图片池大小
//        builder.setMemoryCache(new LruResourceCache(memorySize));
//        builder.setBitmapPool(new LruBitmapPool(memorySize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }


}
