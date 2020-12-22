package com.example.pixabyimagesearch.di

import com.example.pixabyimagesearch.ui.PhotoDetailFragment
import com.example.pixabyimagesearch.ui.PhotoListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

// class to declare fragments that could be injected with dependencies and could be used only for MainActivity.

@Module
abstract class MainFragmentsBuildersModule
{
    @ContributesAndroidInjector(modules = [PhotoListViewModelModule::class])
    abstract fun contributePhotoListFragment(): PhotoListFragment

    @ContributesAndroidInjector(modules = [PhotoListViewModelModule::class])
    abstract fun contributePhotoDetailFragment(): PhotoDetailFragment
}