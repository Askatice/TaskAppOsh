package com.ssa.taskapp;

import android.app.Application;

import com.ssa.taskapp.utils.Prefs;

public class App extends Application {
    public static Prefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new Prefs(getApplicationContext());
    }
}
