package com.example.pixabyimagesearch.di
import android.app.Application
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.pixabyimagesearch.R
import com.example.pixabyimagesearch.api.PixabayApiService
import com.example.pixabyimagesearch.database.PhotoDao
import com.example.pixabyimagesearch.database.PhotosDatabase
import com.example.pixabyimagesearch.repository.AppExecutors
import com.example.pixabyimagesearch.utils.Constants
import com.example.pixabyimagesearch.utils.Constants.Companion.BASE_URL
import com.example.pixabyimagesearch.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


//Class to delcare global dependencies that will prabbaly not change and should exist along with app.

@Module
object AppModule {

    //Retrofit
    @Singleton
    @Provides
    fun  provideRetrofitInstance(): PixabayApiService
    {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build().create(PixabayApiService::class.java);
    }

    // Glide RequestOptions
    @Singleton
    @Provides
     fun provideRequestOptions(): RequestOptions {
        return RequestOptions.placeholderOf(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
    }

    //Glide
    @Singleton
    @Provides
     fun provideGlideInstance(
        application: Application?,
        requestOptions: RequestOptions?
    ): RequestManager {
        return Glide.with(application!!)
            .setDefaultRequestOptions(requestOptions!!)
    }

    @Singleton
    @Provides
    fun provideDataBase(app: Application): PhotosDatabase
    {
        return Room.databaseBuilder(app, PhotosDatabase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    //PhotoDao
    @Singleton
    @Provides
    fun providePhotoDao(db: PhotosDatabase) : PhotoDao
    {
        return db.photoDao()
    }

    @Singleton
    @Provides
    fun provideAppExecutos(): AppExecutors {
        return AppExecutors()
    }

}