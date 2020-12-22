package com.example.pixabyimagesearch.util.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.pixabyimagesearch.api.ApiSuccessResponse
import com.example.pixabyimagesearch.api.PixabayApiService
import com.example.pixabyimagesearch.util.LiveDataTestUtil.getValue
import com.example.pixabyimagesearch.utils.Constants
import com.example.pixabyimagesearch.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class PixabayApiServiceServiceTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service : PixabayApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService()
    {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(PixabayApiService::class.java);

    }

    @After
    fun stopService()
    {
        mockWebServer.shutdown()
    }

    @Test
    fun getImagesTest()
    {
        enqueueResponse("fruits.json")
        val response = getValue(service.searchPhotos(Constants.API_KEY,"fruits")
        ) as ApiSuccessResponse

        assertThat(response,notNullValue())
        assertThat(response.body.total, CoreMatchers.`is`(30123))
        assertThat(response.body.photos.size, CoreMatchers.`is`(20))
        assertThat(response.body.totalHits, CoreMatchers.`is`(500))

    }

    private fun enqueueResponse(@Suppress("SameParameterValue") fileName: String) {
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}