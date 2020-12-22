package com.example.pixabyimagesearch.models

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(

    indices = [Index("id")],
    primaryKeys = ["id"]
)
data class Photo(
    @field:SerializedName("largeImageURL")
    val largeImageUrl: String,

    @field:SerializedName("webformatHeight")
    val webFormatHeight: Int,

    @field:SerializedName("webformatWidth")
    val webFormatWidth: Int,

    @field:SerializedName("likes")
    val likesCount: Int,

    @field:SerializedName("imageWidth")
    val imageWidth: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName( "user_id")
    val userId: Long,

    @field:SerializedName("views")
    val viewsCount: Long,

    @field:SerializedName("comments")
    val commentsCount: Int,

    @field:SerializedName("pageURL")
    val pageUrl: String,

    @field:SerializedName("imageHeight")
    val imageHeight: Int,

    @field:SerializedName("webformatURL")
    val webFormatURL: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("previewHeight")
    val previewHeight: Int,

    @field:SerializedName("tags")
    val tags: String,

    @field:SerializedName("downloads")
    val downloadsCount: Int,

    @field:SerializedName("user")
    val userName: String,

    @field:SerializedName("favorites")
    val favoritesCount: Int,

    @field:SerializedName("imageSize")
    val imageSize: Long,

    @field:SerializedName("previewWidth")
    val previewWidth: Int,

    @field:SerializedName("userImageURL")
    val userImageUrl: String,

    @field:SerializedName("previewURL")
    val previewUrl: String

)
{

    /**
     * Empty constructor
     */
    constructor() : this(
        "",
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        "",
        -1,
        "",
        "", -1,
        "",
        -1,
        "",
        -1,
        -1,
        -1,
        "",
        ""

    )
}