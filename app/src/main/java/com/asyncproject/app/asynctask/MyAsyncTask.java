package com.asyncproject.app.asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 0; i < 50; i++) {
            if (isCancelled()) {
                break;
            }
            SystemClock.sleep(50);
            publishProgress(i);

        }
        return null;
    }
}
