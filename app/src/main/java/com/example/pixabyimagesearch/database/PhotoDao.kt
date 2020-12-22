package com.example.pixabyimagesearch.database

import android.util.SparseIntArray
import androidx.collection.SparseArrayCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.models.PhotoQuerySearchResult
import java.util.*

@Suppress("JavaCollectionsStaticMethodOnImmutableList")
@Dao
abstract class PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertPhotos(photoList: List<Photo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: PhotoQuerySearchResult)

    @Query("SELECT * FROM Photo WHERE id = :id")
    abstract fun getPhotoById(id: Int): LiveData<Photo>

    @Query("SELECT * FROM Photo WHERE id in (:photoIds)")
    abstract fun getPhotosById(photoIds: List<Int>): LiveData<List<Photo>>

    @Query("SELECT * FROM PhotoQuerySearchResult WHERE searchQuery = :text ")
    abstract fun searchResults(text: String): LiveData<PhotoQuerySearchResult>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Photo>>? {
        val order = SparseArrayCompat<Int>()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return Transformations.map(getPhotosById(repoIds), fun(photos:List<Photo>): List<Photo> {
            Collections.sort(photos) { r1, r2 ->
                val pos1 = order.get(r1.id)
                val pos2 = order.get(r2.id)
                pos1!! - pos2!!
            }
            return photos
        })
    }

}

