package com.yuntian.basecomponent.mvp

import com.yuntian.basecomponent.dragger.AppComponent
import com.yuntian.basecomponent.dragger.DraggerApp
import com.yuntian.baselibs.base.BaseLazyFragment

import javax.inject.Inject

/**
 * description  .
 * Created by ChuYingYan on 2018/4/29.
 */
abstract class BaseMvpFrgament<P : BasePresenter<*, *>> : BaseLazyFragment(), BaseView {


    @Inject
    protected lateinit var mPresenter: P


    protected val applicationComponent: AppComponent
        get() = (mActivity.application as DraggerApp).component()


    public override fun init() {
        setupActivityComponent(applicationComponent)
            mPresenter.onCreate()
    }

    /**
     * 依赖注入的入口
     */
    protected abstract fun setupActivityComponent(appComponent: AppComponent)


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun transmit() {

    }
}
