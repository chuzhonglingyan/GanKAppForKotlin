package com.yuntian.gankappforkotlin.ui.gank.list

import android.view.View
import com.yuntian.adapterlib.base.BaseViewHolder
import com.yuntian.baselibs.glide.ImageLoaderUtil
import com.yuntian.gankappforkotlin.entity.GankInfo
import kotlinx.android.synthetic.main.item_gank_rest_list.view.*

/**
 * description  ItemNewsListBinding.
 * Created by ChuYingYan on 2018/5/4.
 */
class RestViewHolder(itemView: View) : BaseViewHolder<GankInfo>(itemView) {

    override fun onBindViewData(info: GankInfo, pos: Int) {
        ImageLoaderUtil.displayImage(info.url, itemView.iv_welfare_img)
        itemView.tv_welfare_title.text=info.desc
    }


}
