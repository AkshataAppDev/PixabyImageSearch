package com.example.pixabyimagesearch.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.pixabyimagesearch.api.ApiResponse
import com.example.pixabyimagesearch.api.PhotoSearchResponse
import com.example.pixabyimagesearch.api.PixabayApiService
import com.example.pixabyimagesearch.database.PhotoDao
import com.example.pixabyimagesearch.database.PhotosDatabase
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.models.PhotoQuerySearchResult
import com.example.pixabyimagesearch.InstantAppExecutors
import com.example.pixabyimagesearch.util.TestUtil
import com.example.pixabyimagesearch.util.mock
import com.example.pixabyimagesearch.utils.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.*
import retrofit2.Response

class PhotoRepositoryTest
{
    private lateinit var  photoRespository: PhotoRepository
    private val dao = mock(PhotoDao::class.java)
    private val service = mock(PixabayApiService::class.java)


    @Rule
    @JvmField
    val instantRuleExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init()
    {
        val db = mock(PhotosDatabase::class.java)
        `when`(db.photoDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        photoRespository =  PhotoRepository(InstantAppExecutors(),db,dao,service)


    }

    @Test
    fun searchPhotosTest()
    {
        val query = "flowers"
        val ids = arrayListOf(1,2)
        val photo1 = TestUtil.createPhoto(1)
        val photo2 = TestUtil.createPhoto(2)

        val observer = mock<Observer<Resource<List<Photo>>>>()
        val dbSearchResult = MutableLiveData<PhotoQuerySearchResult>()
        val photos = MutableLiveData<List<Photo>>()

        val photoList = arrayListOf(photo1,photo2)
        val apiResponse = PhotoSearchResponse(Math.random().toInt(),photoList,2)

        val callLiveData = MutableLiveData<ApiResponse<PhotoSearchResponse>>()
        `when`(service.searchPhotos(Constants.API_KEY,query)).thenReturn(callLiveData)

        `when`(dao.searchResults(query)).thenReturn(dbSearchResult)

        photoRespository.search(query).observeForever(observer)

        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(service)
        reset(observer)

        doReturn(photos).`when`(dao).loadOrdered(ids)

        dbSearchResult.postValue(null)
        verify(dao, never()).loadOrdered(anyList())

        verify(service).searchPhotos(Constants.API_KEY,query)
        val updatedResult = MutableLiveData<PhotoQuerySearchResult>()

        `when`(dao.searchResults(query)).thenReturn(updatedResult)
        updatedResult.postValue(PhotoQuerySearchResult(query,ids))

        callLiveData.postValue(ApiResponse.create(Response.success(apiResponse)))
        verify(dao).insertPhotos(photoList)
        photos.postValue(photoList)
        verify(observer).onChanged(Resource.success(photoList))
        verifyNoMoreInteractions(service)
    }
}