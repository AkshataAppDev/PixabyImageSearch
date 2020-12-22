package com.example.pixabyimagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.repository.PhotoRepository
import com.example.pixabyimagesearch.repository.Resource
import com.example.pixabyimagesearch.testing.OpenForTesting
import com.example.pixabyimagesearch.utils.AbsentLiveData
import java.util.*
import javax.inject.Inject

@OpenForTesting
class PhotoListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) :
    ViewModel() {

    private var searchQuery = MutableLiveData<String>()

    private val _photoId = MutableLiveData<Int>()

    val results: LiveData<Resource<List<Photo>>> = Transformations
        .switchMap(searchQuery) { search ->
            if (search.isNullOrBlank()) {
                AbsentLiveData.create()
            } else {
                photoRepository.search(search)
            }
        }

    val photo: LiveData<Photo> = Transformations
        .switchMap(_photoId) { id ->
            photoRepository.loadPhotoById(id)
        }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == searchQuery.value) {
            return
        }
        searchQuery.value = input
    }

    fun setId(photoId: Int) {
        _photoId.value = photoId
    }
}