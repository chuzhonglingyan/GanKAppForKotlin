package com.yuntian.baselibs.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import java.util.*

/**
 * description  只缓存Fragment的状态.
 * Created by ChuYingYan on 2018/4/14.
 */
class BaseFPageStateAdapter : FragmentStatePagerAdapter {


    private val data = ArrayList<Fragment>()
    private val titles = ArrayList<String>()
    private var fragmentManager: FragmentManager? = null


    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity.supportFragmentManager) {
        fragmentManager = fragmentActivity.supportFragmentManager
    }

    constructor(fragmentActivity: FragmentActivity, list: List<Fragment>?, titleList: List<String>) : super(fragmentActivity.supportFragmentManager) {
        fragmentManager = fragmentActivity.supportFragmentManager
        if (list != null) {
            data.addAll(list)
        }
        titles.addAll(titleList)
    }

    constructor(fragmentManager: FragmentManager, list: List<Fragment>?, titleList: List<String>) : super(fragmentManager) {
        this.fragmentManager = fragmentManager
        if (list != null) {
            data.addAll(list)
        }
        titles.addAll(titleList)
    }

    constructor(fragmentManager: FragmentManager, list: List<Fragment>, strList: Array<String>) : this(fragmentManager, list, toList(strList)) {}

    fun setData(list: List<Fragment>?) {
        if (list != null) {
            data.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun addFragment(fragment: Fragment?) {
        if (fragment != null) {
            if (fragment.isAdded) {
            } else {
                data.add(fragment)
                notifyDataSetChanged()
            }
        }
    }

    fun addFragment(fragment: Fragment?, viewPager: ViewPager?) {
        if (fragment != null && viewPager != null) {
            if (!fragment.isAdded) {
                data.add(fragment)
                notifyDataSetChanged()
                viewPager.setCurrentItem(data.size - 1, true)
            }
        }
    }

    fun remveFragment(fragment: Fragment?, viewPager: ViewPager?) {
        if (fragment != null && viewPager != null) {
            if (!fragment.isAdded) {
                data.remove(fragment)
                notifyDataSetChanged()
                viewPager.setCurrentItem(data.size - 1, true)
            }
        }
    }

    fun remveFragment(index: Int, viewPager: ViewPager?) {
        if (index < data.size && viewPager != null) {
            data.removeAt(index)
            notifyDataSetChanged()
            if (index > 1) {
                viewPager.setCurrentItem(index - 1, true)
            } else {
                viewPager.setCurrentItem(0, true)
            }
        }
    }


    /**
     * 刷新fragment
     * @param fragments
     * @param mTitles
     */
    fun updateFragments(fragments: List<Fragment>, mTitles: Array<String>) {
        updateFragments(fragments, toList(mTitles))
    }


    /**
     * 刷新fragment
     * @param fragments
     * @param mTitles
     */
    fun updateFragments(fragments: List<Fragment>, mTitles: List<String>) {
        if (data != null && data.size > 0) {
            val ft = fragmentManager!!.beginTransaction()  //移除之前所有的Fragment
            for (f in data) {
                ft.remove(f)
            }
            ft.commitAllowingStateLoss()
            fragmentManager!!.executePendingTransactions()
            titles.clear()
            data.clear()
        }
        titles.addAll(mTitles)
        data.addAll(fragments)
        notifyDataSetChanged()
    }


    /**
     * 刷新fragment
     * @param fm
     * @param fragments
     * @param mTitles
     */
    fun updateFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        if (data.size > 0) {
            val ft = fm.beginTransaction()  //移除之前所有的Fragment
            for (f in data) {
                ft.remove(f)
            }
            ft.commitAllowingStateLoss()
            fm.executePendingTransactions()
            titles.clear()
            data.clear()
        }
        titles.addAll(mTitles)
        data.addAll(fragments)
        notifyDataSetChanged()
    }


    /**
     * 刷新fragment
     * @param fm
     * @param fragments
     * @param mTitles
     */
    fun updateFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: Array<String>) {
        if ( data.size > 0) {
            val ft = fm.beginTransaction()  //移除之前所有的Fragment
            for (f in data) {
                ft.remove(f)
            }
            ft.commitAllowingStateLoss()
            fm.executePendingTransactions()
            titles.clear()
            data.clear()
        }
        titles.addAll(toList(mTitles))
        data.addAll(fragments)
        notifyDataSetChanged()
    }


    fun swapItems(fromPos: Int, toPos: Int) {
        Collections.swap(titles, fromPos, toPos)
        Collections.swap(data, fromPos, toPos)
        notifyDataSetChanged()
    }

    fun modifyTitle(position: Int, title: String) {
        titles[position] = title
        notifyDataSetChanged()
    }

    fun getData(): List<Fragment> {
        return data
    }


    override fun getItem(position: Int): Fragment {
        return data[position]
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    /**
     * 静态方法
     */
    companion object {
        fun toList(strList: Array<String>): List<String> {
            val title = ArrayList<String>()
            for (value in strList) {
                title.add(value)
            }
            return title
        }
    }
}
