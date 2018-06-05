package com.yuntian.baselibs.net.core;


import com.blankj.utilcode.util.Utils;
import com.yuntian.baselibs.BuildConfig;
import com.yuntian.baselibs.net.constant.URLConstant;
import com.yuntian.baselibs.net.converter.CustomerConverter;
import com.yuntian.baselibs.net.interceptor.CacheControlInterceptor;
import com.yuntian.baselibs.util.HttpsUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * description .
 * Created by ChuYingYan on 2018/5/1.
 */
public class NetApi {

    /**
     * 连接超时时间，单位s
     */
    private static final byte DEFAULT_CONNECT_TIMEOUT = 10;
    /**
     * 读超时时间，单位s
     */
    private static final int DEFAULT_READ_TIMEOUT = 10;
    /**
     * 写超时时间，单位s
     */
    private static final int DEFAULT_WRITE_TIMEOUT = 10;


    private Retrofit retrofit;


    static String HOST_URL=URLConstant.BASE_URL;


    private NetApi(){
        this(HOST_URL);
    }

    //构造方法私有
    private NetApi(String url) {

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        //缓存
        File cacheFile = new File(Utils.getApp().getCacheDir(),Utils.getApp().getPackageName()+ "-cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        CacheControlInterceptor cacheControlInterceptor=new CacheControlInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(createHttpLoggingInterceptor())
                //.addInterceptor(new HeaderInterceptor())
                .addInterceptor(cacheControlInterceptor)
                .addNetworkInterceptor(cacheControlInterceptor)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .cache(cache);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(CustomerConverter.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }


    public static NetApi getApi() {
        return getApi(HOST_URL);
    }


    public static NetApi getApi(String url) {
        HOST_URL=url;
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static NetApi INSTANCE = new NetApi();
    }


    public <T> T create(final Class<T> service) {
        T apiService = retrofit.create(service);
        return apiService;
    }


    /**
     * log interceptor
     * @return
     */
    private static HttpLoggingInterceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return loggingInterceptor;
    }

}