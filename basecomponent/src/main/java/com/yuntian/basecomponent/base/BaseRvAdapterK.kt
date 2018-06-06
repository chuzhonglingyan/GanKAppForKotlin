package com.yuntian.basecomponent.base

import com.yuntian.adapterlib.base.BaseRvAdapter
import com.yuntian.adapterlib.base.TypeInterface

/**
 * description  .
 * Created by ChuYingYan on 2018/6/7.
 */
abstract class BaseRvAdapterK<T : TypeInterface> : BaseRvAdapter<T>() {


    fun setAllData(items: List<T>?) {
        super.adds(items)
    }



}
