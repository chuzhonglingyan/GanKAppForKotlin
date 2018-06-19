package com.yuntian.gankappforkotlin.util


import android.text.TextUtils
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_REST
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_WELFARE


/**
 * description  .
 * Created by ChuYingYan on 2018/5/8.
 */
object GankUitl {


    /**
     * all |福利 | 休息视频 |
     *
     * @param datyType
     */
    fun getDataType(datyType: String): Int {
        return when {
            TextUtils.equals(datyType, GANK_WELFARE) -> ViewHolderUtil.ITEM_TYPE_GANK_WELFARE
            TextUtils.equals(datyType, GANK_REST) -> ViewHolderUtil.ITEM_TYPE_GANK_REST
            else -> ViewHolderUtil.ITEM_TYPE_GANK_ARTICLE
        }
    }


    /**
     * 计算图片要显示的高度
     *
     * @param pixel 原始分辨率
     * @param width 要显示的宽度
     * @return
     */
    fun calcPhotoHeight(pixel: String, width: Int): Int {
        var height = 100
        try {
            val index = pixel.indexOf("*")
            if (index != -1) {

                val widthPixel = Integer.parseInt(pixel.substring(0, index))
                val heightPixel = Integer.parseInt(pixel.substring(index + 1))
                height = (heightPixel * (width * 1.0f / widthPixel)).toInt()
            }
        } catch (e: NumberFormatException) {
        }

        return height
    }


    /**
     * https://developer.qiniu.com/dora/manual/1279/basic-processing-images-imageview2*
     *
     * @param url:http://img.gank.io/6ade6383-bc8e-40e4-9919-605901ad0ca5?imageView2/0/w/100/h/100
     * @param width
     * @param height
     */
    fun getRequireImageUrl(url: String, width: Int, height: Int): String? {
        if (url.contains("img.gank.io")) {
            val stringBuilder = StringBuilder()
            stringBuilder.append(url).append("?imageView2/0")
                    .append("/w/").append(width).append("/h/").append(height)
            return stringBuilder.toString()
        }
        return url
    }

}
