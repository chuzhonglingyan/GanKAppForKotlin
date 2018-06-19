package com.yuntian.gankappforkotlin.entity

import com.yuntian.adapterlib.base.TypeInterface
import com.yuntian.gankappforkotlin.util.ViewHolderUtil

class GankInfo : TypeInterface {


    /**
     * _id : 57a4056c421aa91e2606478d
     * createdAt : 2016-08-05T11:18:04.807Z
     * desc : 8.5
     * publishedAt : 2016-08-05T11:31:58.293Z
     * source : chrome
     * type : 福利
     * url : http://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg
     * used : true
     * who : 代码家
     */

    var _id: String? = null   // 可以是 null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: String? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed: Boolean = false
    var who: String? = null



    // 保存图片宽高
    var pixel: String? = null

    var videoImage: String? = null

    var images: List<String>? = null

    var datetype = 0

    override fun getViewType(): Int {
        return ViewHolderUtil.getViewType(GankInfo::class.java, datetype)
    }


}
