package com.tarek360.movies.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import java.lang.IllegalStateException
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Suppress("UNCHECKED_CAST")
@Singleton
class ViewModelFactory
@Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            viewModels[modelClass]?.get() as T
        } catch (e: Exception) {
            throw IllegalStateException(
                "Did you forget to bind the class '${modelClass.name}' to the ViewModelModule?", e
            )
        }
    }
}
