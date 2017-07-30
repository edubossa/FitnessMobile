package com.ews.fitnessmobile;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by wallace on 13/07/17.
 */
public class FitnessApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this); //chrome://inspect
    }
}
