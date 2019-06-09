package com.tarek360.movies.viewmodel

import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

interface MovieViewModelProviders {
    fun of(activity: FragmentActivity): ViewModelProvider
    fun of(fragment: Fragment): ViewModelProvider
}
