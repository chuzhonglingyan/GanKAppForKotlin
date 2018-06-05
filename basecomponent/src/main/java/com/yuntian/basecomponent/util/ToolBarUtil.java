package com.yuntian.basecomponent.util;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * @author guangleilei
 * @version 1.0 2017-04-06
 */
public class ToolBarUtil {


    /**
     * 初始化 Toolbar
     *
     * @param toolbar
     * @param homeAsUpEnabled
     * @param title
     */
    public static void initToolBar(Activity activity, Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        if (activity instanceof AppCompatActivity) {
            AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
            toolbar.setTitle(title);
            appCompatActivity.setSupportActionBar(toolbar);
            appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
        }
    }




}
