package com.yuntian.gankappforkotlin.ui.gank.inject


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.yuntian.adapterlib.base.TypeInterface
import com.yuntian.basecomponent.base.BaseRvAdapterK
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
class GankModule( val view: GankContract.View) {


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
    fun provideModel(model: GankModel): GankContract.Model = model

    @ActivityScope
    @Provides
    fun provideView(): GankContract.View =this.view


    @ActivityScope
    @Provides
    fun providePresenter(presenter: GankPresenter): GankContract.Presenter {
        return presenter
    }

    @ActivityScope
    @Provides
    fun provideBaseAdapter(): BaseRvAdapterK<*> {
        return object : BaseRvAdapterK<TypeInterface>() {}
    }


    @ActivityScope
    @Provides
    fun provideBaseFPageStateAdapter(): BaseFPageStateAdapter {
        return BaseFPageStateAdapter(fragmentActivty!!)
    }

    @ActivityScope
    @Provides
    fun provideBaseFPageAdapter(): BaseFPageAdapter {
        return BaseFPageAdapter(fragmentActivty!!)
    }


}
