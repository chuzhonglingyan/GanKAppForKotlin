package com.yuntian.baselibs.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils

/**
 * description  .
 * Created by ChuYingYan on 2018/4/11.
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: Activity

    protected lateinit var rootView: View
    protected  var args: Bundle? =null

    protected var isCreate = false //fragment创建了
    protected var isViewInitialized = false //view初始化了

    protected abstract val layoutId: Int


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
        mActivity = (context as Activity?)!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreate = true
        if (arguments != null) {
            args = arguments as Bundle
        } else {
            args = Bundle()
        }
        LogUtils.d(this.toString() + ",args=" + args.toString())
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layoutId, container, false)
        isViewInitialized = true
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
        initData(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (args != null) {
            outState.putAll(args)
        }

    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            args = savedInstanceState
        }
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        isViewInitialized = false
    }


    override fun onDestroy() {
        super.onDestroy()
        isCreate = false
    }


    override fun onDetach() {
        super.onDetach()
    }


    override fun getContext(): Context? {
        return mContext
    }

    protected abstract fun init()

    protected abstract fun initView()

    protected abstract fun initData(savedInstanceState: Bundle?)


    /**
     * 此方法是结合FragmentPagerAdapter使用
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)

    }

    /**
     * 此方法是结合add和hide方法使用
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
    }

    companion object {
        private val TAG = "BaseFragment"
    }

}
