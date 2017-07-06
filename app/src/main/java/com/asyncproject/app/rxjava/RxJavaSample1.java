package com.asyncproject.app.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class RxJavaSample1 extends Activity {
    private static final String RX_JAVA_TAG = RxJavaSample1.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        just();
//        from();
//        fromCallable();
        create();
//        defer();
    }

    private void just() {
        Observable.just("Just")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void from() {
        List<String> stringList = Arrays.asList("from1", "from2");
        Observable.from(stringList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void fromCallable() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.v(RX_JAVA_TAG, String.format("Observable.fromCallable: call() in %s thread", Thread.currentThread().getName()));
                return "fromCallable";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void create() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.v(RX_JAVA_TAG, String.format("Observable.create: call() in %s thread", Thread.currentThread().getName()));
                subscriber.onNext("create1");
                subscriber.onNext("create2");
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void defer() {
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                List<String> stringList = Arrays.asList("defer1", "defer2", "defer3");
                return Observable.from(stringList);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.v(RX_JAVA_TAG, String.format("Subscriber: onCompleted() in %s thread", Thread.currentThread().getName()));
        }

        @Override
        public void onError(Throwable e) {
            Log.v(RX_JAVA_TAG, String.format("Subscriber: onError() in %s thread", Thread.currentThread().getName()));
        }

        @Override
        public void onNext(String s) {
            Log.v(RX_JAVA_TAG, String.format("Subscriber: onNext(%s) in %s thread", s, Thread.currentThread().getName()));
        }
    };
}
