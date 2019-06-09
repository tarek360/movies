package com.tarek360.movies.viewmodel

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

class MovieViewModelProvidersImpl(private val factory: ViewModelProvider.Factory): MovieViewModelProviders {
    override fun of(fragment: Fragment): ViewModelProvider {
        return ViewModelProviders.of(fragment, factory)
    }

    override fun of(activity: FragmentActivity): ViewModelProvider {
        return ViewModelProviders.of(activity, factory)
    }
}
