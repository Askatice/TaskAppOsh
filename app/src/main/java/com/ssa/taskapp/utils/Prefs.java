package com.ssa.taskapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    public final static String BOARD_KEY = "boardKey";
    private SharedPreferences preferences;
    public static Prefs prefs;

    public Prefs(Context context) {
        prefs = this;
        preferences = context.getSharedPreferences(BOARD_KEY, Context.MODE_PRIVATE);
    }
    public void isShowed() {
        preferences.edit().putBoolean("show", true).apply();
    }

    public boolean isShown() {
        return preferences.getBoolean("show", false);
    }

    public static Prefs getPrefs() {
        return prefs;
    }

    public void saveFirstName(String name) {
        preferences.edit().putString("first", name).apply();
    }

    public String firstName() {
        return preferences.getString("first", "First name");

    }

    public void saveLastName(String last) {
        preferences.edit().putString("last", last).apply();
    }

    public String lastName() {
        return preferences.getString("last", "Last name");

    }

    public void saveImage(Uri uri) {
        preferences.edit().putString("image", uri.toString()).apply();
    }

    public String getImage() {
        return preferences.getString("image", "");
    }
}