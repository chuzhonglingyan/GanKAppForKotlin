package com.yuntian.basecomponent.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author guangleilei
 * @version 1.0 2017-02-07
 */
public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private float targetY = -1;

    private static final String TAG = FooterBehavior.class.getSimpleName();

    public FooterBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }


    /*
    在方法onStartNestedScroll中，首先获取target在Y轴上距离屏幕顶端的距离，然后判断是否是在Y轴上滚动。
    方法onNestPreScroll中，就是时时根据target距离屏幕顶端的距离计算出滚动的距离，然后根据比例计算出child移动的距离。*/
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {

        if (targetY == -1) {
            targetY = target.getY();
        }
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        float scrooY = targetY - Math.abs(target.getY());
        float scaleY = scrooY / targetY;
        child.setTranslationY(child.getHeight() * scaleY);

    }


}
