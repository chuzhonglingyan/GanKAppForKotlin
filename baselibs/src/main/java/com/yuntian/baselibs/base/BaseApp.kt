package com.yuntian.baselibs.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

import com.blankj.utilcode.util.Utils
import com.yuntian.baselibs.BuildConfig
import com.yuntian.baselibs.glide.ImageLoaderUtil

/**
 * description  .
 * Created by ChuYingYan on 2018/4/29.
 */
open class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)

        ImageLoaderUtil.initImageLoader(this)
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
    }
}
