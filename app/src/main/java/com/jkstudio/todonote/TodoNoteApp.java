package com.jkstudio.todonote;

import android.app.Application;

import androidx.multidex.MultiDex;

public class TodoNoteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }
}
