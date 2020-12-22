package com.example.pixabyimagesearch.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey // Used to map similar dependencies and group them together.
annotation class ViewModelKey(
    val value: KClass<out ViewModel> // general name
)
