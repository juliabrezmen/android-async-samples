package com.asyncproject.app.weakthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import com.asyncproject.app.R;

import java.lang.ref.WeakReference;

public class WeakThreadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity);

        MyThread myThread = new MyThread(this);
        myThread.start();
    }

    private static class MyThread extends Thread {
        private WeakReference<WeakThreadActivity> weakReference;

        public MyThread(WeakThreadActivity activity) {
            this.weakReference = new WeakReference<WeakThreadActivity>(activity);
        }

        @Override
        public void run() {
            SystemClock.sleep(5000);
            final WeakThreadActivity activity = weakReference.get();
            if (activity != null) {
                onLoadingCompleted(activity);
            }
        }

        private void onLoadingCompleted(final WeakThreadActivity activity) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView txtHello = (TextView) activity.findViewById(R.id.txt_hello);
                    txtHello.setText("Hello World");
                }
            });
        }
    }
}
