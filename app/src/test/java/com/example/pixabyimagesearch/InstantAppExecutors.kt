package com.example.pixabyimagesearch

import com.example.pixabyimagesearch.repository.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}