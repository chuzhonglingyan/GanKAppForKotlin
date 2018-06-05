package com.yuntian.baselibs.util;

import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/19.
 */
public class WebkitCookieUtil {



    // 移除指定url关联的所有cookie
    public static void remove(String url) {
        CookieManager cm = CookieManager.getInstance();
        for (String cookie : cm.getCookie(url).split("; ")) {
            cm.setCookie(url, cookie.split("=")[0] + "=");
        }
        flush();
    }

    // sessionOnly 为true表示移除所有会话cookie，否则移除所有cookie
    public static void remove(boolean sessionOnly) {
        CookieManager cm = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (sessionOnly) {
                cm.removeSessionCookies(null);
            } else {
                cm.removeAllCookies(null);
            }
        } else {
            if (sessionOnly) {
                cm.removeSessionCookie();
            } else {
                cm.removeAllCookie();
            }
        }
        flush();
    }

    // 写入磁盘
    public static void flush() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }


}
