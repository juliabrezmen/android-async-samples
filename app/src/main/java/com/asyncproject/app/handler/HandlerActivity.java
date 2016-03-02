package com.asyncproject.app.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class HandlerActivity extends Activity {

    private int count;
    private Handler handler;
    private MyRunnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myRunnable = new MyRunnable();
        handler = new Handler();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(myRunnable);
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            count++;
            Log.i("AsynhProject", "Count: " + count);
            handler.postDelayed(myRunnable, 2000);
        }
    }
}
