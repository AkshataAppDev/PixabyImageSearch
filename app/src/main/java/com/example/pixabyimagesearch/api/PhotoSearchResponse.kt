package com.example.pixabyimagesearch.api

import com.example.pixabyimagesearch.models.Photo
import com.google.gson.annotations.SerializedName


data class PhotoSearchResponse(
    @SerializedName("totalHits")
    val totalHits: Int = 0,
    @SerializedName("hits")
    val photos: List<Photo>,
    @SerializedName("total")
    val total: Int
)