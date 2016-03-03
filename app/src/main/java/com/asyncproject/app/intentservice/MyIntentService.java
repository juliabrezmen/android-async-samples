package com.asyncproject.app.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

public class MyIntentService extends IntentService {
    private static final String KEY_NAME = "key_name";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String text = intent.getStringExtra(KEY_NAME);
        SystemClock.sleep(5000);
        Log.i("AsyncProject", text);
    }

    public static void start(@NonNull String text, @NonNull Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.putExtra(KEY_NAME, text);
        context.startService(intent);
    }
}
