package com.example.pixabyimagesearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pixabyimagesearch.databinding.FragmentPhotoDetailBinding

class PhotoDetailFragment: BaseFragment() {


    private lateinit var binding: FragmentPhotoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPhotoDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photoId = PhotoDetailFragmentArgs.fromBundle(requireArguments()).photoId

        photoListViewModel.setId(photoId)

        photoListViewModel.photo.observe(viewLifecycleOwner, {
            binding.photo = it
        })
    }
}