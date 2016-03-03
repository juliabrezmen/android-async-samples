package com.asyncproject.app.bindservice;

import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.asyncproject.app.R;

public class ServiceActivity extends Activity {
    public final static String BROADCAST_ACTION = "Request status";

    private boolean bound;
    private ServiceConnection serviceConnection;
    private MyService myService;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);

        initBroadcastReceiver();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindMyService();
    }

    private void bindMyService() {
        Intent intent = new Intent(this, MyService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("AsyncProject", "onServiceConnected");
                bound = true;
                myService = ((MyService.MyBinder) service).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("AsyncProject", "onServiceDisconnected");
                bound = false;
            }
        };
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initBroadcastReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String stringExtra = intent.getStringExtra(MyService.KEY_STATUS);
                Log.i("AsyncProject", stringExtra);
            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    private void initView() {
        Button btnLoad = (Button) findViewById(R.id.btn_request);
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bound) {
                    myService.doRequest();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        if (bound) {
            unbindService(serviceConnection);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();
    }
}
