package com.enpassio.androidmvpandmvvmpatterns;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks;

public class NewsApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Preload custom tabs service for improved performance
        // This is optional but recommended
        registerActivityLifecycleCallbacks(new CustomTabsActivityLifecycleCallbacks());
    }
}