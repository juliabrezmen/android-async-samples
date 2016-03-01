package com.asyncproject.app.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.asyncproject.app.R;

public class AsyncActivity extends Activity {
    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.async_task_activity);

        final TextView txtProgress = (TextView) findViewById(R.id.txt_progress);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(50);

        myAsyncTask = new MyAsyncTask() {
            @Override
            protected void onProgressUpdate(Integer... values) {
                progressBar.setProgress(values[0]);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                txtProgress.setText("Completed");
            }

            @Override
            protected void onCancelled() {
                Log.i("AsyncProject", "AsyncTask is canceled");
                super.onCancelled();
            }
        };

        myAsyncTask.execute();
    }

    @Override
    protected void onDestroy() {
        myAsyncTask.cancel(true);
        Log.i("AsyncProject", "onDestroy");
        super.onDestroy();
    }
}
