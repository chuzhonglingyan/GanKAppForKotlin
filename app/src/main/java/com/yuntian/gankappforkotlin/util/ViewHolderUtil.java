package com.yuntian.gankappforkotlin.util;

import com.yuntian.adapterlib.base.ModeViewHolder;
import com.yuntian.adapterlib.base.TypeInterface;
import com.yuntian.adapterlib.base.ViewHolderManager;
import com.yuntian.gankappforkotlin.R;
import com.yuntian.gankappforkotlin.entity.GankInfo;
import com.yuntian.gankappforkotlin.ui.gank.list.ArticleViewHolder;
import com.yuntian.gankappforkotlin.ui.gank.list.RestViewHolder;
import com.yuntian.gankappforkotlin.ui.gank.list.WelfaseViewHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/4.
 */
public class ViewHolderUtil {


    public static final int ITEM_TYPE_GANK_ARTICLE = 0;
    public static final int ITEM_TYPE_GANK_WELFARE = ITEM_TYPE_GANK_ARTICLE+1;
    public static final int ITEM_TYPE_GANK_REST = ITEM_TYPE_GANK_WELFARE+1;


    static {
        Map<Integer, ModeViewHolder> gankMap = new HashMap<>();
        gankMap.put(ITEM_TYPE_GANK_ARTICLE, new ModeViewHolder(R.layout.item_gank_article_list, ArticleViewHolder.class));
        gankMap.put(ITEM_TYPE_GANK_WELFARE, new ModeViewHolder(R.layout.item_gank_welfare_list, WelfaseViewHolder.class));
        gankMap.put(ITEM_TYPE_GANK_REST, new ModeViewHolder(R.layout.item_gank_rest_list, RestViewHolder.class));
        ViewHolderManager.registerTypeClass(GankInfo.class, gankMap);
    }

    public static void init(){

    }

    public static int getViewType(Class<? extends TypeInterface> classData, int customKey) {
        return ViewHolderManager.getViewType(classData, customKey);
    }


}
