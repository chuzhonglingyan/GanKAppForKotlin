package com.yuntian.gankappforkotlin.base

import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.yuntian.basecomponent.dragger.DraggerApp
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.util.ViewHolderUtil


/**
 * description  .
 * Created by ChuYingYan on 2018/5/21.
 */
class GankApp : DraggerApp() {


    override fun onCreate() {
        super.onCreate()

    }

    companion object {

        //static 代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)//全局设置主题颜色
                MaterialHeader(context)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }

            ViewHolderUtil.init()
        }
    }
}
