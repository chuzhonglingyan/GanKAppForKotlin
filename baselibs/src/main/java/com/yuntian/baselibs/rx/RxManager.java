package com.yuntian.baselibs.rx;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/1.
 */
public class RxManager {


    public CompositeDisposable compositeDisposable = new CompositeDisposable();


    public RxManager(){

    }

    public void add(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void clear() {
        compositeDisposable.clear();
    }


}
