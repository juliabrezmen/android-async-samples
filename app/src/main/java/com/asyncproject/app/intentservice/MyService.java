package com.asyncproject.app.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends IntentService {
    private static final String KEY_NAME = "key_name";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String text = intent.getStringExtra(KEY_NAME);
        SystemClock.sleep(5000);
        Log.i("AsyncProject", text);
    }

    public static void start(String text, Context context) {
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra(KEY_NAME, text);
        context.startService(intent);
    }
}
