package com.yuntian.basecomponent.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;



/**
 * description Pop问题很多，建议不再使用.
 * Created by ChuYingYan on 2018/5/20.
 */
@Deprecated
public abstract class BasePopWindow extends PopupWindow {

    protected View popView;
    protected Context mContext;

    public BasePopWindow(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        initPopView();
        initData();
    }


    protected void initPopView() {
        popView = LayoutInflater.from(mContext).inflate(getLayoutResId(),null);
        initView();
        initPop();
    }


    public abstract void initView();

    public abstract void initData();

    public abstract int getLayoutResId();


    protected void initPop() {
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setTouchable(true);
        setContentView(popView);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    }


}
