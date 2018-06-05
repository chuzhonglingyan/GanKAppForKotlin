package com.yuntian.baselibs.rx;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * description  .
 * Created by ChuYingYan on 2018/5/1.
 */
public class RxExample {

    private static final String TAG = "RxExample";


    public void subscribeTest() {

        //上游发布者  被观测对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
            //产生订阅关系
        }).subscribe(new Observer<Integer>() { //下游订阅者  观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });

        //每次emitter.onNext发射一个数据，订阅者的onNext()方法都回调一次
        //onComplete和onError唯一并且互斥这一点,应该在数据发射完成onComplete调用，发生异常会onError()
        //事件队列，先发射，先到达
    }

    public void disposableTest() {
        //注意: 调用dispose()并不会导致上游不再继续发送事件, 上游会继续发送剩余的事件..但是下游不会收到剩余的事件。
        //1,2,3,complete,4
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);
                i++;
                if (i == 2) {
                    Log.d(TAG, "dispose");
                    mDisposable.dispose();
                    Log.d(TAG, "isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });
        //result: emit 1 ,onNext: 1,emit 2,onNext: 2,dispose,isDisposed : true,emit 3,emit complete, emit 4
    }


    public void onlyCareOnNextAndThrowable() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "onNext: " + integer);
            }
        });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "onNext: " + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }


    public void testThreadSchedulers() {

        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
            }
        });
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                    }
                }).observeOn(Schedulers.io()).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
            }
        }).subscribe(consumer);


        // 多次指定上游的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
        //多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
        //doOnNext可以观察数据接收情况
    }


    public void mapOperate() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
        //元素类型转换
    }


    public void flatmapOperate() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
        ////flatMap 一个水管，分成3个水管
        //flatMap中将上游发来的每个事件转换为一个新的发送三个String事件的水管,
        // 为了看到flatMap结果是无序的,所以加了10毫秒的延时, 来看看运行结果吧:

    }

}
