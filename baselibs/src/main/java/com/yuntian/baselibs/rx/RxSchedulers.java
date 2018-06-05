package com.yuntian.baselibs.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/1.
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> io_main() {
        new ObservableTransformer<T,T>(){
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
        return null;
    }


}
