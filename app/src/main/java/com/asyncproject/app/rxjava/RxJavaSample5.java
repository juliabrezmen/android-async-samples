package com.asyncproject.app.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaSample5 extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUsersName()
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .flatMap(new Func1<String, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(String s) {
                        return getAge(s);
                    }
                })
//                .map(new Func1<String, Observable<Integer>>() {
//                    @Override
//                    public Observable<Integer> call(String s) {
//                        return null;
//                    }
//                })
//                .filter(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer>=30;
//                    }
//                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("Age: " + integer);
                    }
                });

        List<String> stringList = Arrays.asList("url10", "url20", "url30");
        Observable.just(stringList)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });


        Observable.from(Arrays.asList("url1", "url2", "url3"))
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });


        Observable.just("Hello world from just")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " Julia";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i("Async", "onNext() " + s);
                    }
                });

        Observable.just("Hello world from just")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " Dima";
                    }
                })
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("Async", "onNext() " + integer);
                    }
                });


        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("Async", "onCompleted()");
            }

            @Override
            public void onError(Throwable t) {
                Log.i("Async", "onError() " + t.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i("Async", "onNext() " + s);
            }
        };


        Observable<String> observableJust = Observable.just("Hello from just");
        observableJust.subscribe(subscriber);


        Observable<String> observableCreate = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello from create");
                subscriber.onError(new Exception("RX fake error"));
//                subscriber.onCompleted();
            }
        });
        observableCreate.subscribe(subscriber);

        observableCreate.subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i("Async", "onNextAction() " + s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("Async", "onError() " + throwable.getMessage());
            }
        });

    }

    Observable<List<String>> getUsersName() {
        List<String> userList = Arrays.asList("user1", "user2", "user3");
        return Observable.just(userList);
    }

    Observable<Integer> getAge(String userName) {
        return Observable.just(30);
    }
}
