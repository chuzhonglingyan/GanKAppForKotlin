package com.yuntian.gankappforkotlin.ui.gank


import android.os.Bundle
import android.support.v4.app.Fragment
import com.yuntian.basecomponent.dragger.AppComponent
import com.yuntian.basecomponent.util.ToolBarUtil
import com.yuntian.baselibs.adapter.BaseFPageAdapter
import com.yuntian.baselibs.util.FragmentHelper
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.storage.cons.AppConstants
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_ARTICLE
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_REST
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_WELFARE
import com.yuntian.gankappforkotlin.ui.gank.inject.DaggerGankComponent
import com.yuntian.gankappforkotlin.ui.gank.inject.GankModule
import kotlinx.android.synthetic.main.fragment_gank_main.view.*
import java.util.*
import javax.inject.Inject

/**
 * description  .
 * Created by ChuYingYan on 2018/5/3.
 */
class GankMainFragment : GankViewFragment() {

    companion object {
        val TAG = "GankMainFragment"
    }

    @Inject
    lateinit var baseFPageAdapter: BaseFPageAdapter


    override val layoutId: Int
        get() = R.layout.fragment_gank_main


    override fun initView() {
        ToolBarUtil.initToolBar(mActivity, rootView.tool_bar, true, "干货")
    }

    override fun initData(savedInstanceState: Bundle?) {

        val fragments = ArrayList<Fragment>()
        val bundlearticle = Bundle()
        bundlearticle.putString(AppConstants.GANK_DATATYPE, GANK_ARTICLE)

        val bundlearWelfare = Bundle()
        bundlearWelfare.putString(AppConstants.GANK_DATATYPE, GANK_WELFARE)

        val bundleRest = Bundle()
        bundleRest.putString(AppConstants.GANK_DATATYPE, GANK_REST)

        fragments.add(FragmentHelper.newInstance(ArticleListFragment::class.java, bundlearticle))
        fragments.add(FragmentHelper.newInstance(ArticleListFragment::class.java, bundlearWelfare))
        fragments.add(FragmentHelper.newInstance(ArticleListFragment::class.java, bundleRest))

        baseFPageAdapter.updateFragments(childFragmentManager, fragments, arrayOf("技术文章", "福利生活", "休息视频"))
        rootView.view_pager.adapter =baseFPageAdapter
        rootView.tab_layout.setupWithViewPager(rootView.view_pager)
    }

    override fun lazyLoad() {

    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerGankComponent
                .builder()
                .appComponent(appComponent)
                .gankModule(GankModule(this))
                .build()
                .inject(this)  //调用inject，注入就完成了。此时使用@Inject来标记成员变量就可以使用了
    }


    override fun showMsg(message: String, code: Int) {

    }


}
