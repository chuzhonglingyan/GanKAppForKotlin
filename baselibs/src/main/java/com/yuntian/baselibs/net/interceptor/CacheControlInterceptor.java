package com.yuntian.baselibs.net.interceptor;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.yuntian.baselibs.net.cache.CacheStrategy.CACHE_CONTROL_CACHE;

/**
 * description 缓存拦截器.
 * Created by ChuYingYan on 2018/5/1.
 */
public class CacheControlInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            LogUtils.d("not netWork");
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isConnected()) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public," + CACHE_CONTROL_CACHE)
                    .removeHeader("Pragma")
                    .build();
        }
    }



}
