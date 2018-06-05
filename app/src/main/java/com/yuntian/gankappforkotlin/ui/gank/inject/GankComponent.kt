package com.yuntian.gankappforkotlin.ui.gank.inject

import com.yuntian.basecomponent.dragger.AppComponent
import com.yuntian.basecomponent.dragger.scope.ActivityScope

import dagger.Component

/**
 * description .
 * Created by ChuYingYan on 2018/5/1.
 */
@ActivityScope
@Component(modules = arrayOf(GankModule::class), dependencies = arrayOf(AppComponent::class))
interface GankComponent


//    void inject(GankMainFragment fragment);
//
//    void inject(WelfareListFragment fragment);
//
//    void inject(RestListFragment fragment);
//
//    void inject(ArticleListFragment fragment);
