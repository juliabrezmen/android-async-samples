package com.asyncproject.app.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaSample3 extends Activity {
    private static final String RX_JAVA_TAG = RxJavaSample3.class.getSimpleName();
    private Subscription subscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscription = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SystemClock.sleep(5000);
                return "Delayed message";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @Override
    protected void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.v(RX_JAVA_TAG, "Subscriber: onCompleted()");
        }

        @Override
        public void onError(Throwable e) {
            Log.v(RX_JAVA_TAG, "Subscriber: onError()");
            e.printStackTrace();
        }

        @Override
        public void onNext(String s) {
            Log.v(RX_JAVA_TAG, String.format("Subscriber: onNext(%s)", s));
        }
    };
}
