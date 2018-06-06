package com.yuntian.gankappforkotlin.ui.gank.inject

import com.yuntian.basecomponent.dragger.AppComponent
import com.yuntian.basecomponent.dragger.scope.ActivityScope
import com.yuntian.gankappforkotlin.ui.gank.ArticleListFragment
import com.yuntian.gankappforkotlin.ui.gank.GankMainFragment
import dagger.Component

/**
 * description .
 * Created by ChuYingYan on 2018/5/1.
 */
@ActivityScope
@Component(modules = arrayOf(GankModule::class), dependencies = arrayOf(AppComponent::class))
interface GankComponent {

    fun inject(fragment: GankMainFragment)

    fun inject(fragment: ArticleListFragment)
//    void inject(WelfareListFragment fragment);
//
//    void inject(RestListFragment fragment);
}




