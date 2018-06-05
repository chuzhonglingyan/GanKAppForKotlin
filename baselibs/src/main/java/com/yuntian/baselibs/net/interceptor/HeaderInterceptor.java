package com.yuntian.baselibs.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description Header拦截器.
 * Created by ChuYingYan on 2018/5/1.
 */
public class HeaderInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request build = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .build();
        return chain.proceed(build);
    }
}
