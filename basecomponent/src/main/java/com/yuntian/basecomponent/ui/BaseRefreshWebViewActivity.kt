package com.yuntian.basecomponent.ui

import android.graphics.Color
import android.support.v7.widget.Toolbar
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import com.blankj.utilcode.util.SizeUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.yuntian.basecomponent.R
import com.yuntian.baselibs.base.BaseWebViewActivity


/**
 * link @(https://juejin.im/post/59a56b2151882524424a1862)
 * https://www.jianshu.com/p/a800e66c8949
 * description WebView基本用法 .
 * Created by ChuYingYan on 2018/5/19.
 */
abstract class BaseRefreshWebViewActivity : BaseWebViewActivity(), OnRefreshListener {


    protected lateinit var smartRefreshLayout: SmartRefreshLayout
    protected lateinit var toolBar: Toolbar


    public override val layoutId: Int
        get() = 0


    public override fun init() {
        rooView = LinearLayout(this)
        rooView.orientation = LinearLayout.VERTICAL
        rooView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        initToolBar()
        initSmartRefreshLayout()
        rooView.addView(toolBar)
        rooView.addView(smartRefreshLayout)
    }


    fun initToolBar() {
        toolBar = Toolbar(this)
        toolBar.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(50f))
        toolBar.setTitleTextColor(Color.WHITE)
        toolBar.minimumHeight = SizeUtils.dp2px(50f)
        toolBar.setNavigationIcon(R.mipmap.icon_back)
        toolBar.setTitleTextAppearance(this, R.style.Toolbar)
        toolBar.setNavigationOnClickListener { finish() }
    }


    fun initSmartRefreshLayout() {
        smartRefreshLayout = SmartRefreshLayout(this)
        smartRefreshLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        smartRefreshLayout.setRefreshHeader(ClassicsHeader(this))
        smartRefreshLayout.isEnableLoadMore = false

        mWebView = WebView(this)
        smartRefreshLayout.addView(mWebView)
        smartRefreshLayout.setOnRefreshListener(this)
    }


}
