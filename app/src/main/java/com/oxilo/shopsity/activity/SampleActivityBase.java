package com.oxilo.shopsity.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oxilo.shopsity.logger.Log;
import com.oxilo.shopsity.logger.LogWrapper;

/**
 * Created by ericbasendra on 13/11/15.
 */
public class SampleActivityBase extends AppCompatActivity{

    public static final String TAG = "SampleActivityBase";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
    }

    /** Set up targets to receive log data */
    public void initializeLogging() {
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        // Wraps Android's native log framework
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);

        Log.i(TAG, "Ready");
    }
}
