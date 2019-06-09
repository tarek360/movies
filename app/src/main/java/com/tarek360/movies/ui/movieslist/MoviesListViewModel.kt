package com.tarek360.movies.ui.movieslist

import com.tarek360.movies.viewmodel.BaseViewModel
import com.tarek360.movies.domain.MoviesListInteractor
import com.tarek360.movies.rx.SchedulerProvider
import timber.log.Timber
import javax.inject.Inject

open class MoviesListViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val moviesListInteractor: MoviesListInteractor
) :
    BaseViewModel<MoviesListIntent, MoviesListState>() {

    override fun handleIntent(intent: MoviesListIntent) {
        when (intent) {
            is MoviesListIntent.LoadMoviesListIntent -> {
                loadMoviesListIntent()
            }
        }
    }

    private fun loadMoviesListIntent() {
        Timber.d("Intent: load movies intent!")

        if (currentViewState == MoviesListState.LoadingState || currentViewState is MoviesListState.DataState) {
            return
        }

        compositeDisposable.add(moviesListInteractor.getMoviesList()
            .map<MoviesListState> { MoviesListState.DataState(it) }
            .startWith(MoviesListState.LoadingState)
            .onErrorReturn { MoviesListState.ErrorState("Error") }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.main())
            .subscribe {
                currentViewState = it
            })
    }

    override fun onCleared() {
        super.onCleared()
        moviesListInteractor.clear()
    }
}
