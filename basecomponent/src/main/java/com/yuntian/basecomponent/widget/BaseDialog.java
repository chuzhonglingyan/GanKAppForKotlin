package com.yuntian.basecomponent.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.yuntian.basecomponent.R;


/**
 * 弹窗基类
 */
public abstract class BaseDialog extends Dialog {


    protected Context mContext;

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.common_dialog);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        init();
    }

    private void init() {
        initWindow();
        initView();
        setCancelable(true);
        setCanceledOnTouchOutside(true); //是否点击外部区域消失
    }


    protected void initWindow() {
        Window window = getWindow();
        //设置无边距
        WindowManager.LayoutParams params = window.getAttributes();

        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.width =d.widthPixels; // 宽度设置为屏幕的0.8
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        params.gravity = getGravity();
        window.setAttributes(params);
        setContentView(getLayoutResId());
    }



    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected abstract void initView();

    protected abstract int getLayoutResId();


}
