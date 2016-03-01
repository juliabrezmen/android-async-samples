package com.asyncproject.app.asynctask;

import android.os.AsyncTask;
import android.os.SystemClock;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        for (int i=0;i<50;i++){
            SystemClock.sleep(50);
           if(!isCancelled()){
            publishProgress(i);
        }}
        return null;
    }
}
