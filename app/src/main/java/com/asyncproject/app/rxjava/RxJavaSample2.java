package com.asyncproject.app.rxjava;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class RxJavaSample2 extends Activity {
    public static final String RX_JAVA_TAG = RxJavaSample2.class.getSimpleName();
    private static final String SHARED_PREFS_NAME = "prefs";
    private static final String KEY_TEST = "KEY_TEST";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
                String string = preferences.getString(KEY_TEST, null);

                // it will produce NullPointerException but the app won't crash
                return string.toLowerCase();
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
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
