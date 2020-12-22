package com.example.pixabyimagesearch.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pixabyimagesearch.util.LiveDataTestUtil.getValue
import com.example.pixabyimagesearch.util.TestUtil
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.notNullValue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDatabaseTest :  DbTest(){

    @Test
    fun insertAndRead()
    {
        val photo = TestUtil.createPhoto(1)
        db.photoDao().insertPhotos(arrayListOf(photo))

        val result = getValue(db.photoDao().getPhotosById(arrayListOf(1)))

        assertThat(result,notNullValue())
        assertThat(result.size.toString(),CoreMatchers.`is`("1"))
    }
}