package com.example.pixabyimagesearch.di

import android.app.Application
import com.example.pixabyimagesearch.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

// AppComponent for Application exists as long as app.

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ActivityBuildersModule::class,
    AppModule::class,
    ViewModelFactoryModule::class])
interface AppComponent : AndroidInjector<MyApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}