package com.example.pixabyimagesearch.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.pixabyimagesearch.di.ViewModelProviderFactory
import com.example.pixabyimagesearch.repository.AppExecutors
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment: DaggerFragment() {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var appExecutors: AppExecutors


    lateinit var photoListViewModel: PhotoListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoListViewModel = activity?.run {
            ViewModelProvider(requireActivity(), providerFactory).get(PhotoListViewModel::class.java)
        }?: throw Exception("Invalid Activity")
    }

}