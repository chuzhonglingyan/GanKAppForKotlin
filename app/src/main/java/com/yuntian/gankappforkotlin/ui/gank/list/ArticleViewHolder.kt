package com.yuntian.gankappforkotlin.ui.gank.list

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.yuntian.adapterlib.base.BaseViewHolder
import com.yuntian.baselibs.glide.ImageLoaderUtil
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.entity.GankInfo
import com.yuntian.gankappforkotlin.route.RouteParameters
import com.yuntian.gankappforkotlin.route.RoutePaths
import com.yuntian.gankappforkotlin.util.GankUitl
import kotlinx.android.synthetic.main.item_gank_article_list.view.*

/**
 * description  ItemNewsListBinding.
 * Created by ChuYingYan on 2018/5/4.
 */
class ArticleViewHolder(itemView: View) : BaseViewHolder<GankInfo>(itemView) {


    override fun onBindViewData(info: GankInfo, pos: Int) {
        //mBinding.setGanArticleItem(info);
        //http://img.gank.io/90db2f35-2e9d-4d75-b5a9-53ee1719b57b
        //此图我只需要 宽度：100 的图片，而无需原始图片，则在请求图片的参数上带上： ?imageView2/0/w/100 即可
        //http://img.gank.io/6ade6383-bc8e-40e4-9919-605901ad0ca5?imageView2/0/w/100
        // var ivIcon: ImageView = getView(R.id.iv_icon)
        val params = itemView.iv_icon.layoutParams
        var urlImage: String = info.images?.get(0).orEmpty()

        ImageLoaderUtil.displayImage(GankUitl.getRequireImageUrl(urlImage, params.width, params.height), itemView.iv_icon)

        itemView.tv_ganArticleItem_desc.setOnClickListener({
            // 跳转并携带参数
            ARouter.getInstance().build(RoutePaths.WEB_PATH)
                    .withString(RouteParameters.URL, info.url)
                    .withString(RouteParameters.TITLE, info.desc)
                    .navigation();
        });

        itemView.tv_ganArticleItem_author.text = "${context.resources.getString(R.string.item_author)}${info.who}"
        itemView.tv_ganArticleItem_publish_time.text = "${context.resources.getString(R.string.item_published)}${info.publishedAt}"
        itemView.tv_ganArticleItem_desc.text = "${context.resources.getString(R.string.item_desc)}${info.desc}"
        itemView.tv_ganArticleItem_url.text = "${context.resources.getString(R.string.item_url)}${info.url}"
    }


}
