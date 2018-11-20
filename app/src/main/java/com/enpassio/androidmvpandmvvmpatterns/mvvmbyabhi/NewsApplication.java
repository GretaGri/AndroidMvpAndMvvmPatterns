package com.enpassio.androidmvpandmvvmpatterns.mvvmbyabhi;

import android.app.Application;

import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks;

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Preload custom tabs service for improved performance
        // This is optional but recommended
        registerActivityLifecycleCallbacks(new CustomTabsActivityLifecycleCallbacks());
    }
}