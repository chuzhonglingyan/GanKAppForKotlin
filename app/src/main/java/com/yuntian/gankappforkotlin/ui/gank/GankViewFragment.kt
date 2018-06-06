package com.yuntian.gankappforkotlin.ui.gank

import com.yuntian.basecomponent.mvp.BaseMvpFrgament
import com.yuntian.gankappforkotlin.entity.GankInfo
import com.yuntian.gankappforkotlin.ui.gank.mvp.GankContract

/**
 * description 适配类 .
 * Created by ChuYingYan on 2018/4/29.
 */
abstract class GankViewFragment : BaseMvpFrgament<GankContract.Presenter>(), GankContract.View {


    override fun getWelfarePhotos(result: List<GankInfo>) {

    }

    override fun showMsg(message: String, code: Int) {

    }


}
