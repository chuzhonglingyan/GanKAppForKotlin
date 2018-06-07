package com.yuntian.gankappforkotlin.ui.web

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.yuntian.basecomponent.ui.BaseRefreshWebViewActivity
import com.yuntian.basecomponent.util.ToolBarUtil
import com.yuntian.baselibs.base.BaseWebViewActivity
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.route.RoutePaths.WEB_PATH


/**
 * description  .
 * Created by ChuYingYan on 2018/5/19.
 */
@Route(path = WEB_PATH)
class WebViewActvity : BaseRefreshWebViewActivity() {

    @Autowired
    @JvmField var url: String?=""

    @Autowired
    @JvmField var title: String?=""



    override fun initData(savedInstanceState: Bundle?) {

        toolBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        ToolBarUtil.initToolBar(mActivity, toolBar, true, title)
        // mWebView.loadUrl(url);
        smartRefreshLayout.autoRefresh()
        // ARouter会自动对字段进行赋值，无需主动获取
        LogUtils.d("url:", url + ",title:" + title)
    }


    override fun onRefresh(refreshLayout: RefreshLayout) {
        mWebView.loadUrl(url)
    }


    override fun initWebChromeClient() {
        super.initWebChromeClient()

        mWebView.webViewClient = object : BaseWebViewActivity.BaseWebViewClient() {

            private var startUrl: String = ""

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                startUrl = url
                LogUtils.d("startUrl:" + startUrl)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                if (url == null) return false

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url)
                        return true
                    } else {
                        //                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        //                    startActivity(intent);
                        return true
                    }
                } catch (e: Exception) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false
                }

            }
        }
    }

}
