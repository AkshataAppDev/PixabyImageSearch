package com.example.pixabyimagesearch

import com.example.pixabyimagesearch.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree


class MyApplication : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        // returns AppComponent
        return DaggerAppComponent.builder().application(this).build();
    }

}