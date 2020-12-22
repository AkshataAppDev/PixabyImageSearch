package com.example.pixabyimagesearch.models

import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.pixabyimagesearch.database.ListConverter

@Entity(primaryKeys = ["searchQuery"])
@TypeConverters(ListConverter::class)
data class PhotoQuerySearchResult(

    val searchQuery: String,

    val photoIds : List<Int>,

)
