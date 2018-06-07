package com.yuntian.gankappforkotlin.ui.gank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.yuntian.adapterlib.base.TypeInterface
import com.yuntian.adapterlib.listener.OnItemDataClickListenerImp
import com.yuntian.adapterlib.util.RecyclerViewUtil
import com.yuntian.basecomponent.base.BaseRvAdapterK
import com.yuntian.basecomponent.dragger.AppComponent
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.entity.GankInfo
import com.yuntian.gankappforkotlin.storage.cons.AppConstants
import com.yuntian.gankappforkotlin.ui.gank.inject.DaggerGankComponent
import com.yuntian.gankappforkotlin.ui.gank.inject.GankModule
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.frgment_smart_list.view.*
import javax.inject.Inject

class ArticleListFragment : GankViewFragment() {

    private  var dataType: String = ""
    private var startPage: Int = 0

    @Inject
    lateinit var baseRvAdapter: BaseRvAdapterK<*>


    override val layoutId: Int
        get() = R.layout.frgment_smart_list


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCache = true
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        startPage = 0
        dataType = args?.getString(AppConstants.GANK_DATATYPE).orEmpty()

        if (dataType.equals(AppConstants.GANK_WELFARE)) {
            RecyclerViewUtil.initRecyclerViewSV(rootView.rv, baseRvAdapter, 2)
        } else {
            RecyclerViewUtil.initRecyclerViewV(rootView.rv, baseRvAdapter)
        }

        rootView.rv.itemAnimator = SlideInUpAnimator()

        baseRvAdapter.setOnItemDataClickListener(object : OnItemDataClickListenerImp<TypeInterface>() {
            override fun onItemClick(view: View?, item: Any?, truePos: Int, relativePos: Int) {
                super.onItemClick(view, item, truePos, relativePos)
                LogUtils.d("pos=" + truePos + "," + (item as GankInfo)._id)
            }
        })

        rootView.refreshLayout.setOnRefreshListener({
            startPage = 0
            mPresenter.getWelfarePhotos(dataType, startPage)
        })
        rootView.refreshLayout.isEnableLoadMore = false
        rootView.refreshLayout.setOnLoadMoreListener({
            mPresenter.getWelfarePhotos(dataType, startPage)
        })
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun lazyLoad() {
        if (dataType.isNotBlank()) {
            mPresenter.getWelfarePhotos(dataType, startPage)
        }
    }


    override fun showMsg(message: String, code: Int) {
        super.showMsg(message, code)
        if (startPage == 0) {
            hasLoad = false
            rootView.refreshLayout.finishRefresh(1000, false)
        } else {
            rootView.refreshLayout.finishLoadMore(1000, false, false)
        }
    }

    override fun getWelfarePhotos(result: List<GankInfo>) {
        super.getWelfarePhotos(result)
        if (startPage == 0) {
            baseRvAdapter.data = result
            rootView.refreshLayout.finishRefresh()
            rootView.refreshLayout.isEnableLoadMore = true
            hasLoad = true
        } else {
            baseRvAdapter.allData = result
            rootView.refreshLayout.finishLoadMore()
        }
        if (result.isNotEmpty()) {
            startPage += 1
        } else {
            rootView.refreshLayout.finishLoadMoreWithNoMoreData()
        }
    }


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerGankComponent
                .builder()
                .appComponent(appComponent)
                .gankModule(GankModule(this))
                .build()
                .inject(this)  //调用inject，注入就完成了。此时使用@Inject来标记成员变量就可以使用了
    }

}


