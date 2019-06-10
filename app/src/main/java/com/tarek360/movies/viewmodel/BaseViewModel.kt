package com.tarek360.movies.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel<Intent, State, Action> : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private val viewStateLiveData = MutableLiveData<State>()

    val viewState: LiveData<State>
        get() = viewStateLiveData

    protected var currentViewState: State? = null
        set(value) {
            field = value
            viewStateLiveData.value = value
        }

    protected val viewActionLiveData = MutableLiveData<Action>()

    val viewAction: LiveData<Action>
        get() = viewActionLiveData

    fun setViewAction(action: Action) {
        viewActionLiveData.value = action
    }

    abstract fun handleIntent(intent: Intent)

    @CallSuper
    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}
