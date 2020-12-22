package com.example.pixabyimagesearch.api

import androidx.lifecycle.LiveData
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApiService {

    //https://pixabay.com/api/?key=19547688-c0cafc35b2fd328d3f72ad1a8&q=fruits
    @GET("api/")
    fun searchPhotos (
        @Query("key") ApiKey: String,
        @Query("q") query: String,
    ) : LiveData<ApiResponse<PhotoSearchResponse>>
}
