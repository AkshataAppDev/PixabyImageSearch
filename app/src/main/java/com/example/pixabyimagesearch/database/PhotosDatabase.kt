package com.example.pixabyimagesearch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.models.PhotoQuerySearchResult

@Database(
    entities = [Photo::class,PhotoQuerySearchResult::class],
    version = 1,
    exportSchema = false
)
abstract class PhotosDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}