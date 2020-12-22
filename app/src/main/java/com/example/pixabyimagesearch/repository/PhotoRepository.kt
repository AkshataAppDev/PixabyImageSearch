package com.example.pixabyimagesearch.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.pixabyimagesearch.api.ApiResponse
import com.example.pixabyimagesearch.api.ApiSuccessResponse
import com.example.pixabyimagesearch.api.PhotoSearchResponse
import com.example.pixabyimagesearch.api.PixabayApiService
import com.example.pixabyimagesearch.database.PhotoDao
import com.example.pixabyimagesearch.database.PhotosDatabase
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.models.PhotoQuerySearchResult
import com.example.pixabyimagesearch.testing.OpenForTesting
import com.example.pixabyimagesearch.utils.AbsentLiveData
import com.example.pixabyimagesearch.utils.Constants
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
class PhotoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val photoDatabase: PhotosDatabase,
    private val photoDao: PhotoDao,
    private val photoApiService: PixabayApiService
) {

    private val TAG = "PhotoRepository"

    fun loadPhotoById(photoId: Int): LiveData<Photo> {
        return photoDao.getPhotoById(photoId)
    }

    fun search(query: String): LiveData<Resource<List<Photo>>> {
        return object : NetworkBoundResource<List<Photo>, PhotoSearchResponse>(appExecutors) {

            override fun saveCallResult(item: PhotoSearchResponse) {

                val ids = arrayListOf<Int>()
                val photoIds = item.photos.map { it.id }
                ids.addAll(photoIds)

                val repoSearchResult = PhotoQuerySearchResult(
                    searchQuery = query,
                    photoIds = photoIds
                )

                Timber.d("Now saving to database")
                photoDatabase.runInTransaction {
                    photoDao.insertPhotos(item.photos)
                    photoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Photo>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb(): LiveData<List<Photo>> {
                return Transformations.switchMap(
                    photoDao.searchResults(
                        query
                    )
                ) { searchData ->

                    if (searchData == null) {
                        Timber.d( "Data not present")
                        AbsentLiveData.create()
                    } else {
                        Timber.d( "Getting it from  database")
                        photoDao.loadOrdered(searchData.photoIds)
                    }
                }
            }

            override fun createCall(): LiveData<ApiResponse<PhotoSearchResponse>> {
                return photoApiService.searchPhotos(Constants.API_KEY, query)
            }

            override fun processResponse(response: ApiSuccessResponse<PhotoSearchResponse>)
                    : PhotoSearchResponse {
                val body = response.body
                return body
            }

        }.asLiveData()
    }

}