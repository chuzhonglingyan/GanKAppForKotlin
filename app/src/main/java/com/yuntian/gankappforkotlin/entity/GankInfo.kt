/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
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
