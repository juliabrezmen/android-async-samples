package com.asyncproject.app.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {
    public static final String KEY_STATUS = "key status";
    private MyBinder binder = new MyBinder();
    private ExecutorService fixedExecutor;

    @Override
    public void onCreate() {
        super.onCreate();
        fixedExecutor = Executors.newFixedThreadPool(1);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void doRequest() {
        fixedExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("AsynhProject", "Requesting..");
                SystemClock.sleep(3000);
                Intent intent = new Intent(ServiceActivity.BROADCAST_ACTION);
                intent.putExtra(KEY_STATUS, "Done");
                sendBroadcast(intent);
            }
        });
    }

    class MyBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }
}
