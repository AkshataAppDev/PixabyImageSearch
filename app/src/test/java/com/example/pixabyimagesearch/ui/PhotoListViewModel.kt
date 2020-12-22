package com.example.pixabyimagesearch.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pixabyimagesearch.models.Photo
import com.example.pixabyimagesearch.repository.PhotoRepository
import com.example.pixabyimagesearch.repository.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.*

@RunWith(JUnit4::class)
class PhotoListViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()
    private val repository = mock(PhotoRepository::class.java)
    private lateinit var viewModel: PhotoListViewModel

    @Before
    fun init() {

        viewModel = PhotoListViewModel(repository)

    }

    @Test
    fun emptyResults()
    {
        val result = com.example.pixabyimagesearch.util.mock<Observer<Resource<List<Photo>>>>()
        viewModel.results.observeForever(result)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun basicTest()
    {
        val result = com.example.pixabyimagesearch.util.mock<Observer<Resource<List<Photo>>>>()
        viewModel.results.observeForever(result)
        viewModel.setQuery("query")
        verify(repository).search("query")
    }
}

