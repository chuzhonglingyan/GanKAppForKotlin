package com.yuntian.gankappforkotlin.ui.gank.mvp

import com.yuntian.basecomponent.mvp.BaseMvpFrgament
import com.yuntian.gankappforkotlin.entity.GankInfo

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
