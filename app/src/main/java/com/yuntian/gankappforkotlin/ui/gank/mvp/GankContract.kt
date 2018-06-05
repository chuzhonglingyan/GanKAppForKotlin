package com.yuntian.gankappforkotlin.ui.gank.mvp


import com.yuntian.basecomponent.mvp.BaseModel
import com.yuntian.basecomponent.mvp.BasePresenter
import com.yuntian.basecomponent.mvp.BaseView
import com.yuntian.gankappforkotlin.entity.GankInfo

import io.reactivex.Observable

/**
 * description 合约类 .
 * Created by ChuYingYan on 2018/4/29.
 */
interface GankContract {

    interface View : BaseView {

        fun getWelfarePhotos(result: List<GankInfo>)
    }

    abstract class Presenter : BasePresenter<Model, View>() {

        abstract fun getWelfarePhotos(datatypeStr: String, startPage: Int)
    }

    interface Model : BaseModel {
        fun getWelfarePhotos(datatypeStr: String, startPage: Int): Observable<List<GankInfo>>
    }
}
