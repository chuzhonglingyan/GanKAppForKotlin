package com.yuntian.gankappforkotlin.ui.gank.mvp

import android.text.TextUtils
import com.yuntian.baselibs.glide.ImageLoaderUtil
import com.yuntian.baselibs.net.core.NetApi
import com.yuntian.baselibs.net.result.RxHandleResult
import com.yuntian.gankappforkotlin.entity.GankInfo
import com.yuntian.gankappforkotlin.net.GankService
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_ARTICLE
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_REST
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_WELFARE
import com.yuntian.gankappforkotlin.util.GankUitl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * description  M层.
 * Created by ChuYingYan on 2018/4/29.
 */
class GankModel @Inject
constructor() : GankContract.Model {


    override fun getWelfarePhotos(datatypeStr: String, startPage: Int): Observable<List<GankInfo>> {

        return when {
            TextUtils.equals(datatypeStr, GANK_ARTICLE) -> NetApi.getApi().create(GankService::class.java)
                    .getWelfarePhotos(datatypeStr, startPage)
                    .compose(RxHandleResult.handleResult())
                    .concatMap { list -> Observable.fromIterable(list) }
                    .map { ganInfo ->
                        ganInfo.datetype = GankUitl.getDataType(datatypeStr)
                        ganInfo
                    }
                    .filter { ganInfo -> !(ganInfo.type == GANK_WELFARE || ganInfo.type == GANK_REST) }
                    .toList().toFlowable().toObservable()
            TextUtils.equals(datatypeStr, GANK_WELFARE) -> NetApi.getApi().create(GankService::class.java)
                    .getWelfarePhotos(datatypeStr, startPage)
                    .compose(RxHandleResult.handleResult())
                    .concatMap { list -> Observable.fromIterable(list) }
                    .map { ganInfo ->
                        ganInfo.datetype = GankUitl.getDataType(datatypeStr)
                        ganInfo
                    }
                    .observeOn(Schedulers.io())
                    .filter { ganInfo ->
                        try {
                            ganInfo.pixel = ImageLoaderUtil.calePhotoSize(ganInfo.url);
                            true;
                        } catch (e: Exception) {
                            e.printStackTrace()
                            false
                        }
                    }
                    .toList().toFlowable().toObservable()
                    .observeOn(AndroidSchedulers.mainThread());
            else -> NetApi.getApi().create(GankService::class.java)
                    .getWelfarePhotos(datatypeStr, startPage)
                    .compose(RxHandleResult.handleResult())
                    .concatMap { list -> Observable.fromIterable(list) }
                    .map({ ganInfo ->
                        ganInfo.datetype = GankUitl.getDataType(datatypeStr)
                        ganInfo
                    })
                    .toList().toFlowable().toObservable()
        }
    }

}
