package com.asyncproject.app.timer;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class TimerActivity extends Activity {

    private CountDownTimer timer;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timer = new CountDownTimer(TimeUnit.SECONDS.toMillis(10), TimeUnit.SECONDS.toMillis(2)) {
            @Override
            public void onTick(long millisUntilFinished) {
                count ++;
                Log.i("AsyncProject", "Count: " + count);
            }

            @Override
            public void onFinish() {
                timer.start();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.start();
    }

    @Override
    protected void onStop() {
        timer.cancel();
        super.onStop();
    }
}
