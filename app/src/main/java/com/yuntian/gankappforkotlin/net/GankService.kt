package com.yuntian.gankappforkotlin.net


import com.yuntian.baselibs.net.entity.rep.BaseResponse
import com.yuntian.gankappforkotlin.entity.GankInfo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * description  .
 * Created by ChuYingYan on 2018/5/8.
 */
interface GankService {

    /**
     * 数据类型：福利 | Android | iOS | 休息视频 | 拓展资源 |前端
     * 获取福利图片
     * eg: http://gank.io/api/data/福利/10/1
     * @param page 页码
     * @return
     */
    @GET("http://gank.io/api/data/{dataType}/10/{page}")
    fun getWelfarePhotos(@Path("dataType") dataType: String, @Path("page") page: Int): Observable<BaseResponse<List<GankInfo>>>

}
