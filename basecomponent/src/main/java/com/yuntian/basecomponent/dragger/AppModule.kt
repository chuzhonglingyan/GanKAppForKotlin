package com.yuntian.basecomponent.dragger

import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * description  .
 * Created by ChuYingYan on 2018/6/5.
 */
@Module
class AppModule(val app: DraggerApp) {

    @Provides
    @Singleton
    fun provideApplication(): DraggerApp = app


}