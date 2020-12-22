package com.example.pixabyimagesearch.di

import com.example.pixabyimagesearch.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

// Class to declare all activities that could be injected with dependencies.

@Module
 abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [MainFragmentsBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

}