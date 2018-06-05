package com.yuntian.baselibs.net.result;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.yuntian.baselibs.rx.RxManager;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/1.
 */
public abstract   class CustomObserver<T> implements Observer<T> {


    private RxManager rxManager;


    public CustomObserver() {
       this(null);
    }


    public CustomObserver(RxManager rxManager) {
        this.rxManager=rxManager;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (rxManager!=null){
            rxManager.add(d);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        //网络
        if (!NetworkUtils.isConnected()) {
            _onError("网络无法连接，请检查网络", 1000);
        } else {
            ErrorMsg errorMsg = new ErrorMsg();
            errorMsg.handleException(e);
            if (errorMsg.getCode()==ErrorMsg.ERR_NULL){
                _onNext(null);
            }else {
                LogUtils.e("msg=" + errorMsg.getError() + ",code" + errorMsg.getCode());
                _onError(errorMsg.getError(),errorMsg.getCode());
            }
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message, int code);


}
