package com.yuntian.baselibs.util;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * description Fragment的返回栈由Activity管理；而Activity的返回栈由系统管理。.
 * Created by ChuYingYan on 2018/4/11.
 */
public class FragmentHelper {


    public static <T extends Fragment> T newInstance(Class<? extends Fragment> fragmentClass) {
        return newInstance(fragmentClass, null);
    }

    public static <T extends Fragment> T newInstance(Class<? extends Fragment> fragmentClass, Bundle args) {
        if (fragmentClass == null) {
            return null;
        }
        try {
            Constructor c = fragmentClass.getConstructor();
            Fragment fragment = (Fragment) c.newInstance();
            if (args != null) {
                fragment.setArguments(args);
            }
            return (T) fragment;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获得当前activity的fragment的实例
     *
     * @param fragmentActivity
     * @param fragmentClass
     * @param <T>
     * @return
     */
    public static <T extends Fragment> T getFragment(FragmentActivity fragmentActivity, Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            return newInstance(fragmentClass);
        }
        return (T) fragment;
    }

    /**
     * 重复利用fragment实例，并解决add重叠问题,保证当前显示一个fragment视图
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragmentClass
     * @param <T>
     * @return
     */
    public static <T extends Fragment> T addOrShowFragment(FragmentActivity fragmentActivity, int containerId, Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = getFragment(fragmentActivity, fragmentClass, tag);
        if (fragment.isAdded()) {
            showFragment(fragmentActivity, fragment);
        } else {
            addFragment(fragmentActivity, containerId, fragment, tag);
        }
        return (T) fragment;
    }


    /**
     * 重复利用fragment实例，并解决add重叠问题,保证当前显示一个fragment视图,添加到stack里面去
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragmentClass
     * @param <T>
     * @return
     */
    public static <T extends Fragment> T addOrShowStackFragment(FragmentActivity fragmentActivity, int containerId, Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = getFragment(fragmentActivity, fragmentClass, tag);
        if (fragment.isAdded()) {
            showFragment(fragmentActivity, fragment);
        } else {
            addToBackStack(fragmentActivity, containerId, fragment, tag);
        }
        return (T) fragment;
    }

    /**
     * 重复利用fragment实例，并解决add重叠问题,保证当前显示一个fragment视图,添加到stack里面去
     *
     * @param fragmentActivity
     * @param containerId
     * @param <T>
     * @return
     */
    public static <T extends Fragment> T addOrShowStackFragment(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        if (fragment.isAdded()) {
            showFragment(fragmentActivity, fragment);
        } else {
            addToBackStack(fragmentActivity, containerId, fragment, tag);
        }
        return (T) fragment;
    }


    /**
     * 隐藏Fragment (show()，hide()最终是让Fragment的View setVisibility(true还是false)，不会调用生命周期)
     *
     * @param fragment
     */
    public void hideFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            transaction.hide(fragment);
            transaction.commit();
        }
    }


    /**
     * 显示指定的Fragment(show()，hide()最终是让Fragment的View setVisibility(true还是false)，不会调用生命周期)
     */
    public static void showFragment(FragmentActivity fragmentActivity, Fragment fragment, List<Fragment> fragmentList) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            if (fragmentList.size() > 0) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    Fragment tempFragment = fragmentList.get(i);
                    if (fragment == tempFragment) {
                        transaction.show(tempFragment);
                    } else {
                        transaction.hide(tempFragment);
                    }
                }
                transaction.commit();
            }
        }
    }


    /**
     * 显示指定的Fragment(show()，hide()最终是让Fragment的View setVisibility(true还是false)，不会调用生命周期)
     */
    public static void hideAllFragment(FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        if (fragmentList.size() > 0) {
            for (int i = 0; i < fragmentList.size(); i++) {
                Fragment tempFragment = fragmentList.get(i);
                if (tempFragment!=null){
                    transaction.hide(tempFragment);
                }
            }
            transaction.commit();
        }
    }


    /**
     * 显示指定的Fragment(show()，hide()最终是让Fragment的View setVisibility(true还是false)，不会调用生命周期)
     *
     * @param fragment
     */
    public static void showFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
            List<Fragment> fragmentList = fragmentActivity.getSupportFragmentManager().getFragments();
            if (fragmentList.size() > 0) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    Fragment tempFragment = fragmentList.get(i);
                    if (fragment == tempFragment) {
                        transaction.show(tempFragment);
                    } else {
                        transaction.hide(tempFragment);
                    }
                }
                transaction.commit();
            }
        }
    }


    public static List<Fragment> getAllFragment(FragmentActivity fragmentActivity) {
        if (fragmentActivity == null) {
            return null;
        }
        return fragmentActivity.getSupportFragmentManager().getFragments();
    }


    /**
     * 替换当前栈顶fragmentPre,并且fragmentPre视图被销毁，回收内存
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void replaceFragment(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.commit();
    }

    /**
     * 替换当前栈顶fragmentPre,fragmentPre被压入栈中，回退栈
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void replaceToBackStack(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(containerId, fragment, tag);
        transaction.addToBackStack(null); //此处的stackName决定前后frgament是否在一个栈中
        transaction.commit();
    }

    /**
     * 隐藏当前的fragment,创建显示当前添加fragment
     *
     * @param fragmentActivity
     * @param containerId
     */
    public static Fragment addHideShowFragment(FragmentActivity fragmentActivity, List<Fragment> fragmentList, Class<? extends Fragment> fragmentClass, int containerId, String tag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(fragmentActivity, fragmentClass, tag);
        hideAllFragment(fragmentActivity, fragmentList);
        if (!fragment.isAdded()) {
            transaction.add(containerId, fragment, tag);
            transaction.commit();
            if (!fragmentList.contains(fragment)) {
                fragmentList.add(fragment);
            }
        } else {
            transaction.show(fragment);
            transaction.commit();
        }
        return fragment;
    }


    /**
     * 获得当前显示的fragment
     *
     * @param fragmentActivity
     * @return
     */
    public static Fragment getVisibleFragment(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }


    /**
     * 隐藏当前的fragment,显示当前添加fragment
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void hideAddFragment(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment topFragment = fragmentManager.findFragmentById(containerId);
        transaction.setReorderingAllowed(true);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (topFragment != null) {
            transaction.hide(topFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(containerId, fragment, tag);
            transaction.commit();
        } else {
            transaction.show(fragment);
            transaction.commit();
        }
    }

    /**
     * 隐藏当前的fragment,添加fragment到回退栈，并处于栈顶
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void hideAddToBackStack(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            Fragment topFragment = fragmentManager.findFragmentById(containerId);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.setReorderingAllowed(true);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (topFragment != null) {
                transaction.hide(topFragment);
            }
            transaction.add(containerId, fragment, tag);
            transaction.addToBackStack(null); //此处的stackName决定前后frgament是否在一个栈中
            transaction.commit();
        }
    }


    /**
     * 添加一个fragment到栈顶,并是重叠，此时返回直接退出当前activity
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void addFragment(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        if (!fragment.isAdded()) {
            FragmentManager manager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(containerId, fragment, tag);
            transaction.commit();
        }
    }

    /**
     * 添加fragment到回退栈里面去,直接加入到栈顶
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void addToBackStack(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag) {
        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, tag);
            transaction.addToBackStack(null); //此处的stackName决定前后frgament是否在一个栈中
            transaction.commit();
        }
    }


    /**
     * 添加fragment到回退栈里面去,直接加入到栈顶
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragment
     */
    public static void addToBackStack(FragmentActivity fragmentActivity, int containerId, Fragment fragment, String tag, String name) {
        if (!fragment.isAdded()) {
            FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, tag);
            transaction.addToBackStack(name); //此处的stackName决定前后frgament是否在一个栈中
            transaction.commit();
        }
    }


    /**
     * 批量添加fragment到回退栈里面去
     *
     * @param fragmentActivity
     * @param containerId
     * @param fragments
     */
    public static void addsToBackStack(FragmentActivity fragmentActivity, int containerId, List<Fragment> fragments) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        Fragment topFragment = fragmentManager.findFragmentById(containerId);
        FragmentTransaction transaction = fragmentManager.beginTransaction();  //transaction是一个事务
        transaction.setReorderingAllowed(true);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (fragments.size() > 0) {
            if (topFragment != null) {
                transaction.hide(topFragment);
            }
            for (int i = 0; i < fragments.size(); i++) {
                transaction.add(containerId, fragments.get(i), fragments.get(i).getTag());
            }
            transaction.addToBackStack(fragments.get(0).getTag());  //栈的名字
            transaction.commit();
        }
    }


    /**
     * 移除某个fragment，没有将Fragment加入回退栈，remove方法可以正常出栈
     *
     * @param fragmentActivity
     * @param fragment
     */
    public static void removeFragment(FragmentActivity fragmentActivity, Fragment fragment) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    /**
     * 移除某个fragment，没有将Fragment加入回退栈，remove方法可以正常出栈
     *
     * @param fragmentActivity
     */
    public static void removeFragmentAtIndex(FragmentActivity fragmentActivity, int index) {
        FragmentManager manager = fragmentActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        List<Fragment> fragments = manager.getFragments();
        if (index < fragments.size() && index >= 0) {
            for (int i = 0; i < fragments.size(); i++) {
                if (index == i) {
                    transaction.remove(fragments.get(i));
                    transaction.commit();
                    break;
                }
            }
        }
    }

    /**
     * 获得该fragment栈上面的一个fragment
     *
     * @param fragmentActivity
     * @param fragment
     * @return
     */
    public static Fragment getLatterFragment(FragmentActivity fragmentActivity, Fragment fragment, String tag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        int index = findIndexAtBackStack(fragmentActivity, fragment, tag);
        if (index > -1 && index < count - 1) {  //index 0 being the bottom of the stack.
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index + 1);
            return fragmentManager.findFragmentByTag(backStackEntry.getName());
        }
        return null;
    }

    /**
     * 获得该fragment栈下面的一个fragment
     *
     * @param fragmentActivity
     * @param fragment
     * @return
     */
    public static Fragment getAheadFragment(FragmentActivity fragmentActivity, Fragment fragment, String tag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        int index = findIndexAtBackStack(fragmentActivity, fragment, tag);
        if (index > 0 && index < count) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index - 1);
            return fragmentManager.findFragmentByTag(backStackEntry.getName());
        }
        return null;
    }

    public static int findIndexAtBackStack(FragmentActivity fragmentActivity, Fragment fragment, String tag) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        int index = -1;
        for (int i = 0; i < count; i++) {
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(i);
            if (TextUtils.equals(tag, backStackEntry.getName())) {
                index = i;
            }
        }
        return index;
    }


    /**
     * 通过tag找到该Fragment
     *
     * @param tag
     * @return
     */
    public static Fragment findDescendantFragment(@NonNull FragmentManager fragmentManager, @NonNull String tag) {
        Fragment target = fragmentManager.findFragmentByTag(tag);
        if (target == null) {
            List<Fragment> fragments = fragmentManager.getFragments();
            int count = fragments.size();
            for (int i = count - 1; i > -1; i--) {
                Fragment f = fragments.get(i);
                target = findDescendantFragment(f.getChildFragmentManager(), tag);
                if (target != null) {
                    break;
                }
            }
        }
        return target;
    }

    /**
     * 隐藏Fragment (show()，hide()最终是让Fragment的View setVisibility(true还是false)，不会调用生命周期)
     */
    public void hideFragmentAll(@NonNull FragmentManager fragmentManager, Fragment paerentFragment) {
        if (paerentFragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(paerentFragment);
            List<Fragment> fragments = fragmentManager.getFragments();
            int count = fragments.size();
            for (int i = count - 1; i > -1; i--) {
                Fragment f = fragments.get(i);
                hideFragmentAll(f.getChildFragmentManager(), f);
                if (f == null) {
                    break;
                }
                transaction.hide(f);

            }
            transaction.commit();
        }
    }


    /**
     * 是加入到主线队列的末尾，等其它任务完成后才开始出栈，
     *
     * @param fragmentActivity
     */
    public static void popBackStack(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        // popBackStackImmediate:是队列内的任务立即执行，再将出栈任务放到队列尾（可以理解为立即出栈）。
        fragmentManager.popBackStack();
    }

    /**
     * 如果你想出栈多个Fragment，你应尽量使用popBackStackImmediate(tag/id)，
     * 而不是popBackStack(tag/id)，如果你想在出栈后，立刻beginTransaction()开始一项事务，
     * 你应该把事务的代码post/postDelay到主线程的消息队列里
     * popBackStackImmediate:是队列内的任务立即执行，再将出栈任务放到队列尾（可以理解为立即出栈）。
     *
     * @param fragmentActivity
     */
    public static void popBackStackImmediate(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();
    }

    public static void popBackStackImmediate(FragmentActivity fragmentActivity, String name) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(name, 0);
    }

}
