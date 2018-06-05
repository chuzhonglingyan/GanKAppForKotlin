package com.yuntian.basecomponent.dragger

import dagger.Component
import javax.inject.Singleton


/**
 * description  .
 * Created by ChuYingYan on 2018/6/5.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent  {
    fun inject(app: DraggerApp)
}