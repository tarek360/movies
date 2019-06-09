package com.tarek360.movies.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<Intent, State> : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val liveData = MutableLiveData<State>()

    val viewState: LiveData<State>
        get() = liveData

    protected var currentViewState: State? = null
        set(value) {
            field = value
            liveData.value = value
        }

    abstract fun handleIntent(intent: Intent)

    @CallSuper
    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
