package com.yuntian.baselibs.listener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

/**
 * @explain webview下载
 *
 * @author guangleilei
 *
 * @time 2016/7/28 19:19.
 */

public class MyWebViewDownLoadListener implements DownloadListener {


    public MyWebViewDownLoadListener(Activity acitivity) {
        this.acitivity = acitivity;
    }

    private Activity acitivity;

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                long contentLength) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            acitivity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}