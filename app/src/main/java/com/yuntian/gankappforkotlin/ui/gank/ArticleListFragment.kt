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
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.GANK_DATATYPE
import com.yuntian.gankappforkotlin.ui.gank.inject.DaggerGankComponent
import com.yuntian.gankappforkotlin.ui.gank.inject.GankModule
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.frgment_smart_list.*
import javax.inject.Inject

class ArticleListFragment : GankViewFragment() {

    private lateinit var dataType: String
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
        RecyclerViewUtil.initRecyclerViewV(rv, baseRvAdapter)
        rv.itemAnimator = SlideInUpAnimator()

        baseRvAdapter.setOnItemDataClickListener(object : OnItemDataClickListenerImp<TypeInterface>() {
            override fun onItemClick(view: View?, item: Any?, truePos: Int, relativePos: Int) {
                super.onItemClick(view, item, truePos, relativePos)
                LogUtils.d("pos=" + truePos + "," + (item as GankInfo)._id)
            }
        })

        refreshLayout.setOnRefreshListener({
            startPage = 0
            mPresenter.getWelfarePhotos(dataType, startPage)
        })
        refreshLayout.isEnableLoadMore = false
        refreshLayout.setOnLoadMoreListener({
            mPresenter.getWelfarePhotos(dataType, startPage)
        })
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun lazyLoad() {
        dataType = args.getString(GANK_DATATYPE)
        mPresenter.getWelfarePhotos(dataType, startPage)
    }


    override fun showMsg(message: String, code: Int) {
        super.showMsg(message, code)
        if (startPage == 0) {
            hasLoad = false
            refreshLayout.finishRefresh(1000, false)
        } else {
            refreshLayout.finishLoadMore(1000, false, false)
        }
    }

    override fun getWelfarePhotos(result: List<GankInfo>) {
        super.getWelfarePhotos(result)
        if (startPage == 0) {
            baseRvAdapter.data = result
            refreshLayout.finishRefresh()
            refreshLayout.isEnableLoadMore = true
            hasLoad = true
        } else {
            baseRvAdapter.allData = result
            refreshLayout.finishLoadMore()
        }
        if (result.isNotEmpty()) {
            startPage += 1
        } else {
            refreshLayout.finishLoadMoreWithNoMoreData()
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


