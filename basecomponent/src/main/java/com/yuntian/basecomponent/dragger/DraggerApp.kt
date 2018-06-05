package com.yuntian.basecomponent.dragger

import com.yuntian.baselibs.base.BaseApp


/**
 * description  .
 * Created by ChuYingYan on 2018/6/5.
 */
open class DraggerApp : BaseApp() {


    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent= DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build();
    }

    fun component(): AppComponent {
        return appComponent
    }



}