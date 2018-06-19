package com.yuntian.gankappforkotlin.ui.gank.list

import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.yuntian.adapterlib.base.BaseViewHolder
import com.yuntian.baselibs.glide.ImageLoaderUtil
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.entity.GankInfo
import com.yuntian.gankappforkotlin.util.GankUitl


/**
 * description  .
 * Created by ChuYingYan on 2018/5/8.
 */
class WelfaseViewHolder(itemView: View) : BaseViewHolder<GankInfo>(itemView) {

    // 图片的宽度
    private val mPhotoWidth: Int

    init {
        val widthPixels = context.resources.displayMetrics.widthPixels
        mPhotoWidth = widthPixels / 2 - SizeUtils.dp2px(1f)
    }

    override fun onBindViewData(info: GankInfo, pos: Int) {
        val ivIcon: ImageView = getView(R.id.iv_welfare_img)
        // 返回的数据有像素分辨率，根据这个来缩放图片大小
        val params = ivIcon.layoutParams

        params.width = mPhotoWidth
        params.height = GankUitl.calcPhotoHeight(info.pixel.orEmpty(), mPhotoWidth)

        ivIcon.layoutParams = params

        ImageLoaderUtil.displayImage(info.url, ivIcon)
    }

}
