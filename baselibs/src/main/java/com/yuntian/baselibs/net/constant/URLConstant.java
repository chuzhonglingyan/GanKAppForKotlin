package com.yuntian.baselibs.net.constant;


import com.yuntian.baselibs.BuildConfig;

/**
 * description .
 * Created by ChuYingYan on 2018/5/1.
 */
public class URLConstant {

    public static String DEV_URL = "";

    public static String TEST_URL = "http://gank.io/";

    public static String STG_URL = "https://yuntian.com";

    public static String PRO_URL = "https://yuntian.com";

    public static String BASE_URL = "http://gank.io/";


    static {
        if (!BuildConfig.DEBUG) { // release
            BASE_URL = URLConstant.PRO_URL;
        } else { // debug
            switch (BuildConfig.API_ENV) {
                case "DEV_URL":
                    BASE_URL = URLConstant.DEV_URL;
                    break;
                case "STG_URL":
                    BASE_URL = URLConstant.STG_URL;
                    break;
                default:
                    BASE_URL = URLConstant.TEST_URL;
            }
        }
    }

}
