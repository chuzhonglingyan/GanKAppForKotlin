package com.yuntian.gankappforkotlin.ui.gank.mvp

import com.yuntian.basecomponent.dragger.scope.ActivityScope
import com.yuntian.baselibs.net.result.CustomObserver
import com.yuntian.gankappforkotlin.entity.GankInfo

import javax.inject.Inject

/**
 * description  P层.
 * Created by ChuYingYan on 2018/4/29.
 */
@ActivityScope
class GankPresenter @Inject
constructor() : GankContract.Presenter() {


    override fun getWelfarePhotos(dataTtpe: String, startPage: Int) {
        mModel.getWelfarePhotos(dataTtpe, startPage)
                .subscribe(object : CustomObserver<List<GankInfo>>() {
                    override fun _onNext(photoGirls: List<GankInfo>) {
                        mView.getWelfarePhotos(photoGirls)
                    }

                    override fun _onError(message: String, code: Int) {
                        mView.showMsg(message, code)
                    }
                })
    }
}
