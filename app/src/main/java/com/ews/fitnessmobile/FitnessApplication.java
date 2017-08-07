package com.ews.fitnessmobile;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import io.fabric.sdk.android.Fabric;

/**
 * Created by wallace on 13/07/17.
 */
public class FitnessApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this); //chrome://inspect
        Fabric.with(this, new Crashlytics());
    }
}
