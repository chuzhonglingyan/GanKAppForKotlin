package com.yuntian.baselibs.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blankj.utilcode.util.LogUtils


/**
 * description  .
 * Created by ChuYingYan on 2018/4/11.
 */
abstract class BaseLazyFragment : BaseFragment() {

    protected var isUserVisible = false
    protected var hasLoad = false
    protected  var isCache = false
    protected var isInitialized = false  //view是否已经初始化
    private var isPrepared = true

    /**
     * 此方法是结合FragmentPagerAdapter使用
     * 初始化的时候，第一个fragment会执行两次：先false，再true,执行在onCreateView之前
     * 初始化的时候，均执行在onCreateView之前
     * 初始化之后，均执行在onResume之后
     *
     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isUserVisible = true
            onVisible()
        } else {
            isUserVisible = false
            onInvisible()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (isCache&&isInitialized){
            return rootView
        }
        rootView= super.onCreateView(inflater, container, savedInstanceState)!!
        isInitialized = true

        init()
        initView()
        initData(savedInstanceState)
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (isUserVisible && isPrepared) {
            lazyLoad()
            isPrepared = false
        }
    }

    protected abstract fun lazyLoad()


    /**
     * 可见
     */
    protected fun onVisible() {
        if (isInitialized && !hasLoad) { //第一个fragment有点特殊，没初始化就true
            LogUtils.d("lazy加载开始" + ",args:" + args.toString())
            lazyLoad()
        } else {
            if (hasLoad) {
                LogUtils.d("lazy已经加载成功：hasLoad=" + hasLoad + ",args:" + args.toString())
            } else {
                lazyLoad()
            }
        }
    }

    /**
     * 不可见
     */
    protected fun onInvisible() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (isCache) {
            val parent = rootView.parent as ViewGroup
            parent.removeView(rootView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isUserVisible = false
        hasLoad = false
        isCache = false
        isInitialized = false
        isPrepared = true
    }
}
