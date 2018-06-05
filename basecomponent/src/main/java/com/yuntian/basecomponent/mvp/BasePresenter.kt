package com.yuntian.basecomponent.mvp

import javax.inject.Inject


/**
 * description P层.
 * Created by ChuYingYan on 2018/4/30.
 */
abstract class BasePresenter<M : BaseModel, V : BaseView> : IBasePresenter {


    @Inject
    protected lateinit var mModel: M
    @Inject
    protected lateinit var mView: V


    override fun onCreate() {}

    override fun onStart() {

    }


    override fun onResume() {

    }


    override fun onPause() {

    }


    override fun onStop() {

    }

    override fun onDestroy() {

    }
}
