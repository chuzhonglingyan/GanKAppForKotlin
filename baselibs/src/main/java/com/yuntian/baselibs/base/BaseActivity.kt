package com.yuntian.baselibs.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.alibaba.android.arouter.launcher.ARouter

/**
 * description Activity基类 .
 * Created by ChuYingYan on 2018/4/28.
 */
abstract class BaseActivity : AppCompatActivity() {


     lateinit var mContext: Context
     lateinit var mActivity: Activity
     abstract val layoutId: Int


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mActivity = this
        ARouter.getInstance().inject(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT  // 设置竖屏

        init()
        initView()
        initData(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }


    //open==public
    open fun init() {
        setContentView(layoutId)
    }

    abstract fun initView()

    abstract fun initData(savedInstanceState: Bundle?)


    override fun onDestroy() {
        super.onDestroy()
    }
}
