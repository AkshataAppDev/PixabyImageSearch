package com.example.pixabyimagesearch.di

import androidx.lifecycle.ViewModel
import com.example.pixabyimagesearch.ui.PhotoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

//Module class for the ViewModel itself.

@Module
abstract class PhotoListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PhotoListViewModel::class) // mapping viewmodel into viewmodel key.
    abstract fun bindPhotoListViewModel(newsListViewModel: PhotoListViewModel): ViewModel
}