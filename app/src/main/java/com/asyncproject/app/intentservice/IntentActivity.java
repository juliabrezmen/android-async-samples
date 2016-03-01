package com.asyncproject.app.intentservice;

import android.app.Activity;
import android.os.Bundle;
import com.asyncproject.app.R;

public class IntentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_activity);
        MyService.start("Hello from me", this);
    }
}
