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
import rx.subscriptions.CompositeSubscription;

public class RxJavaSample4 extends Activity {
    private static final String RX_JAVA_TAG = RxJavaSample4.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompositeSubscription compositeSubscription = new CompositeSubscription();
        Subscriber<String> subscriber1 = createNewSubscriber("Subscriber1");
        Subscriber<String> subscriber2 = createNewSubscriber("Subscriber2");

        Subscription subscription1 = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SystemClock.sleep(5000);
                return "Delayed message";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);

        compositeSubscription.add(subscription1);

        compositeSubscription.unsubscribe();

        // the next code won't be invoked because it goes after unsubscribe()
        Subscription subscription2 = Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SystemClock.sleep(5000);
                return "Delayed message";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber2);

        compositeSubscription.add(subscription2);
    }

    private Subscriber<String> createNewSubscriber(final String name) {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.v(RX_JAVA_TAG, String.format("%s: onCompleted()", name));
            }

            @Override
            public void onError(Throwable e) {
                Log.v(RX_JAVA_TAG, String.format("%s: onError()", name));
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                Log.v(RX_JAVA_TAG, String.format("%s: onNext(%s)", name, s));
            }
        };
    }
}
