package com.ssa.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssa.taskapp.database.AppDateBase;
import com.ssa.taskapp.utils.Prefs;

public class App extends Application {
    public static Prefs prefs;
    public static AppDateBase dateBase;
    public static FirebaseAuth auth;
    public static FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new Prefs(getApplicationContext());
        auth = FirebaseAuth.getInstance();
        user = App.auth.getCurrentUser();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    dateBase = Room.databaseBuilder(getApplicationContext(),
            AppDateBase.class,
            "database").
            fallbackToDestructiveMigration().
            allowMainThreadQueries().
            build();
    }
}
