package com.ssa.taskapp;

import android.app.Application;

import androidx.room.Room;

import com.ssa.taskapp.database.AppDateBase;
import com.ssa.taskapp.utils.Prefs;

public class App extends Application {
    public static Prefs prefs;
    public static AppDateBase dateBase;

    @Override
    public void onCreate() {
        super.onCreate();
        prefs = new Prefs(getApplicationContext());
    dateBase = Room.databaseBuilder(getApplicationContext(),
            AppDateBase.class,
            "database").
            fallbackToDestructiveMigration().
            allowMainThreadQueries().
            build();
    }
}
