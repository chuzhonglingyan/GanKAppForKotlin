package com.yuntian.baselibs.net.result;


import com.yuntian.baselibs.net.entity.rep.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author guangleilei
 * @explain 对服务器返回数据成功和失败处理
 * @time 2017/2/22 13:43.
 */
public class RxHandleResult {


    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .to(new Function<Observable<BaseResponse<T>>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(Observable<BaseResponse<T>> baseResponseObservable) throws Exception {
                                return baseResponseObservable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                                    @Override
                                    public ObservableSource<T> apply(BaseResponse<T> result) throws Exception {
                                        if (result.success()) {
                                            return createData(result.getData());
                                        } else {
                                            return Observable.error(new ServerException(result.getMsg(), result.getCode()));
                                        }
                                    }
                                });
                            }
                        });
            }
        };
    }

    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult1() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .to(new Function<Observable<BaseResponse<T>>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(Observable<BaseResponse<T>> baseResponseObservable) throws Exception {
                                return baseResponseObservable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                                    @Override
                                    public ObservableSource<T> apply(BaseResponse<T> result) throws Exception {
                                        if (result.success()) {
                                            return createData(result.getData());
                                        } else {
                                            return Observable.error(new ServerException(result.getMsg(), result.getCode()));
                                        }
                                    }
                                });
                            }
                        });
            }
        };
    }


    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResultWork() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.to(new Function<Observable<BaseResponse<T>>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(Observable<BaseResponse<T>> baseResponseObservable) throws Exception {
                        return baseResponseObservable.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseResponse<T> result) throws Exception {
                                if (result.success()) {
                                    return createData(result.getData());
                                } else {
                                    return Observable.error(new ServerException(result.getMsg(), result.getCode()));
                                }
                            }
                        });
                    }
                });
            }
        };
    }


    private static <T> ObservableSource<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    if (data!=null){
                        emitter.onNext(data);
                        emitter.onComplete();
                    }else {
                        emitter.onError(new ServerException("对象为空", ErrorMsg.ERR_NULL));
                    }
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }



}
