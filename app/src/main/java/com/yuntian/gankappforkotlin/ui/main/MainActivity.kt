package com.yuntian.gankappforkotlin.ui.main

import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.yuntian.baselibs.base.BaseActivity
import com.yuntian.gankappforkotlin.R
import com.yuntian.gankappforkotlin.storage.cons.AppConstants.HOME_CURRENT_TAB_POSITION
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * 首页
 */
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {


    var mItemId: Int = R.id.nav_camera  //用var定义变量,用val定义常量（相当于final）

    lateinit var stackTabs: Stack<Int>  //延迟初始化
    lateinit var fragmentList: MutableList<Fragment>


    override val layoutId: Int
        get() = R.layout.activity_main


    override fun initView() {
        initDrawerLayout(drawer_layout, nav_view)
    }

    override fun initData(savedInstanceState: Bundle?) {
        stackTabs = Stack()
        fragmentList = mutableListOf()

        if (savedInstanceState != null) {
            mItemId = savedInstanceState.getInt(HOME_CURRENT_TAB_POSITION)
        }
        switchTo(mItemId)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(HOME_CURRENT_TAB_POSITION, mItemId)
        LogUtils.d("HOME_CURRENT_TAB_POSITION=" + mItemId)
    }

    fun initDrawerLayout(drawerLayout: DrawerLayout, navView: NavigationView) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
            //将侧边栏顶部延伸至status bar
            drawerLayout.fitsSystemWindows = true
            //将主页面顶部延伸至status bar
            drawerLayout.clipToPadding = false
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerClosed(drawerView: View) {
                switchTo(mItemId)
            }
        })
        navView.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        mItemId = item.itemId
        return item.isChecked
    }

    /**
     * 切换
     */
    private fun switchTo(position: Int) {
        when (position) {
        //新闻
            R.id.nav_camera -> {
                stackTabs.clear()
                //FragmentHelper.addHideShowFragment(this, fragmentList, GankMainFragment::class.java, R.id.fl_container, GankMainFragment.TAG)
                addToStack(R.id.nav_camera)
            }
        //图片
            R.id.nav_gallery -> {
                //FragmentHelper.addHideShowFragment(this, fragmentList, PhotoMainFragment::class.java, R.id.fl_container, PhotoMainFragment.TAG)
                addToStack(R.id.nav_gallery)
            }
        //视频
            R.id.nav_slideshow -> ToastUtils.showShort("视频")
        //设置
            R.id.nav_manage -> ToastUtils.showShort("设置")
            else -> {

            }
        }
    }


    fun addToStack(id: Int) {
        if (!stackTabs.contains(id)) {
            stackTabs.push(id)
        }
    }


    override fun onBackPressed() {
        // 获取堆栈里有几个
        val stackEntryCount = stackTabs.size
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (stackEntryCount <= 1) {
            exit() // 如果剩一个说明在主页，提示按两次退出app
        } else {
            val tabId = stackTabs[stackEntryCount - 2]
            nav_view.setCheckedItem(tabId)
            stackTabs.pop()
            switchTo(tabId)
            mItemId = tabId
        }
    }


    var mExitTime: Long = 0

    /**
     * 退出
     */
    fun exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ToastUtils.showShort("再按一次退出程序")
            mExitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentList.clear()
        stackTabs.clear()
    }

}
