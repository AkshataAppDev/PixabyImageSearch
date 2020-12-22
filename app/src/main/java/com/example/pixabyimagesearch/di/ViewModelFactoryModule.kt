package com.example.pixabyimagesearch.di
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

//Class for the custom ViewModelProviderFactory
@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory

}