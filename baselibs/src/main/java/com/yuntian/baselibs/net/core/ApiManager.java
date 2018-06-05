package com.yuntian.baselibs.net.core;


import android.support.v4.util.ArrayMap;

/**
 * description Api管理类 .
 * Created by ChuYingYan on 2018/5/1.
 */
public class ApiManager {

    public static ArrayMap<String, NetApi> apiMap = new ArrayMap<>();


    public static NetApi getApi(String url) {
        NetApi netApi = apiMap.get(url);
        if (netApi == null) {
            netApi = NetApi.getApi(url);
            apiMap.put(url, netApi);
        }
        return netApi;
    }

    public static NetApi getApi() {
        return getApi(NetApi.HOST_URL);
    }

}
