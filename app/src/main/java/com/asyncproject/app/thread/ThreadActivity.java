package com.asyncproject.app.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import com.asyncproject.app.R;

public class ThreadActivity extends Activity {
    private TextView txtHello;
    private TextView txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_activity);

        initView();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtHello.setText("Hello");
                    }
                });
            }
        });
        thread.start();

        txtName.setText("World");
    }

    private void initView() {
        txtHello = (TextView) findViewById(R.id.txt_hello);
        txtName = (TextView) findViewById(R.id.txt_name);
    }
}
