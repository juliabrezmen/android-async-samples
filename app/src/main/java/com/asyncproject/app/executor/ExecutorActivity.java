package com.asyncproject.app.executor;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        singleExecute();
//        fixedExecute();
    }

    private void singleExecute() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        newSingleThreadExecutor.execute(new MyRunnable(this));
        newSingleThreadExecutor.execute(new MyRunnable(this));
        newSingleThreadExecutor.execute(new MyRunnable(this));
        newSingleThreadExecutor.execute(new MyRunnable(this));
        newSingleThreadExecutor.execute(new MyRunnable(this));
    }

    private void fixedExecute() {
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        newFixedThreadPool.execute(new MyRunnable(this));
        newFixedThreadPool.execute(new MyRunnable(this));
        newFixedThreadPool.execute(new MyRunnable(this));
        newFixedThreadPool.execute(new MyRunnable(this));
        newFixedThreadPool.execute(new MyRunnable(this));
    }

    private static class MyRunnable implements Runnable {
        private WeakReference<ExecutorActivity> weakReference;

        public MyRunnable(ExecutorActivity activity) {
            weakReference = new WeakReference<ExecutorActivity>(activity);
        }

        @Override
        public void run() {
            SystemClock.sleep(5000);
            ExecutorActivity activity = weakReference.get();
            if (activity != null) {
                Log.i("AsyncProject", "Hello world");
            }
        }
    }
}
