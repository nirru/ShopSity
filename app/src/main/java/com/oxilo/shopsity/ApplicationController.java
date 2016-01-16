package com.oxilo.shopsity;

/*
 All Copyright, Audianz Network Pvt ltd.
CIN:
All intellectual property, code ownership belongs un-conditionally
to Audianz Network Pvt Ltd. No unauthorised code copying,
redistribution and editing is permitted.
Author: Audianz Network Pvt Ltd
CIN:
*/

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.oxilo.shopsity.MODAL.MobiKytePrefs;
import com.path.android.jobqueue.JobManager;
import com.path.android.jobqueue.config.Configuration;
import com.path.android.jobqueue.log.CustomLogger;
import com.splunk.mint.Mint;

/**
 * Created by ericbasendra on 15/11/15.
 */
public class ApplicationController extends Application {

    public static final int LONG_DELAY = 3500; // 3.5 seconds
    public static final int SHORT_DELAY = 2000; // 2 seconds
    /**
     * REGISTARTION REGUEST TAG
     */
    public static final String REGISTRATION_TAG = "RegistrationTag";

    /**
     * LOGIN REGUEST TAG
     */
    public static final String LOGIN_TAG = "LoginTag";

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance;

    private JobManager jobManager;

    private MobiKytePrefs mobiKytePrefs;

    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        sInstance = this;

        Mint.initAndStartSession(sInstance, "24e2e9c4");

        mobiKytePrefs = MobiKytePrefs.getComplexPreferences(getBaseContext(), "mobikyte", MODE_PRIVATE);

        configureJobManager();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


    private void configureJobManager() {
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";
                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }


                    @Override
                    public void d(String text, Object... args) {
                        Log.d(TAG, String.format(text, args));
                    }


                    @Override
                    public void e(Throwable t, String text, Object... args) {
                        Log.e(TAG, String.format(text, args), t);
                    }


                    @Override
                    public void e(String text, Object... args) {
                        Log.e(TAG, String.format(text, args));
                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(this, configuration);
    }


    public JobManager getJobManager() {
        return jobManager;
    }

    public MobiKytePrefs getMobiKytePrefs() {
        if(mobiKytePrefs != null) {
            return mobiKytePrefs;
        }
        return null;
    }

}
