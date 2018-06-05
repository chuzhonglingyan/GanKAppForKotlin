package com.yuntian.gankappforkotlin.ui.gank.inject


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

import com.yuntian.adapterlib.base.BaseRvAdapter
import com.yuntian.adapterlib.base.TypeInterface
import com.yuntian.basecomponent.dragger.scope.ActivityScope
import com.yuntian.baselibs.adapter.BaseFPageAdapter
import com.yuntian.baselibs.adapter.BaseFPageStateAdapter
import com.yuntian.gankappforkotlin.ui.gank.mvp.GankContract
import com.yuntian.gankappforkotlin.ui.gank.mvp.GankModel
import com.yuntian.gankappforkotlin.ui.gank.mvp.GankPresenter

import dagger.Module
import dagger.Provides

/**
 * description .
 * Created by ChuYingYan on 2018/5/1.
 */
@Module
class GankModule(private val view: GankContract.View) {


    val fragmentActivty: FragmentActivity?
        get() {
            if (view is FragmentActivity) {
                return view
            } else if (view is Fragment) {
                return (view as Fragment).activity
            }
            return null
        }


    @ActivityScope
    @Provides
    internal fun provideModel(model: GankModel): GankContract.Model = model

    @ActivityScope
    @Provides
    internal fun provideView(): GankContract.View {
        return this.view
    }


    @ActivityScope
    @Provides
    internal fun providePresenter(presenter: GankPresenter): GankContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    internal fun provideBaseAdapter(): BaseRvAdapter<*> {
        return object : BaseRvAdapter<TypeInterface>() {}
    }


    @ActivityScope
    @Provides
    internal fun provideBaseFPageStateAdapter(): BaseFPageStateAdapter {
        return BaseFPageStateAdapter(fragmentActivty!!)
    }

    @ActivityScope
    @Provides
    internal fun provideBaseFPageAdapter(): BaseFPageAdapter {
        return BaseFPageAdapter(fragmentActivty!!)
    }


}
